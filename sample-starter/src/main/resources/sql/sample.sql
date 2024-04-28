#todo
你的建表语句,包含索引

-- 订单表建表语句，目前包含基础信息，并且冗余部分字段信息(例如下列注释内容)，简化查询难度
-- user_id和sku_id的索引，关注绝大部分业务场景下的查询要求，加快查询性能
CREATE TABLE `order`
(
    `id`             bigint(20) NOT NULL COMMENT '订单号',
    `user_id`        varchar(128)   NOT NULL COMMENT '购买人',
--     `user_name`   varchar(255)   NOT NULL COMMENT '购买人名称',
    `sku_id`         varchar(128)   NOT NULL COMMENT 'SkuId',
--     `sku_name`    varchar(255)   NOT NULL COMMENT '商品名称',
    `transaction_id` varchar(128)   NOT NULL COMMENT '交易ID',
    `amount`         int(11) NOT NULL COMMENT '购买数量',
    `money`          decimal(10, 2) NOT NULL COMMENT '购买金额',
    `pay_time`       datetime       NOT NULL COMMENT '购买时间',
    `pay_status`     varchar(20)    NOT NULL COMMENT '支付状态字典值',
    `del_flag`       tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标志（0代表存在，1代表删除）',
    `create_by`      varchar(255)   NOT NULL COMMENT '创建人',
    `create_time`    datetime       NOT NULL COMMENT '创建时间',
    `update_by`      varchar(255)   NOT NULL COMMENT '修改人',
    `update_time`    datetime       NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`),
    KEY              `idx_user_id` (`user_id`),
    KEY              `idx_sku_id` (`sku_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- 在大数据量下,结合实际业务场景,可以考虑使用复合索引,避免全表扫描
-- 以淘宝为例,在全部订单中,买家一般根据买家id和支付状态查询订单,并根据创建时间倒序排列
CREATE INDEX idx_buyer_orders ON `order` (user_id, pay_status, create_time DESC);
-- 卖家查询的实时性要求相对较低，但考虑到数据量级较大，为了保证查询性能，仍建议创建索引来支持卖家通过SkuID和支付状态查询，并且根据创建时间倒序排列
CREATE INDEX idx_seller_orders ON `order` (sku_id, pay_status, create_time DESC);

-- 首先明确,在数据量非常庞大的情况下,查询性能会受到影响,无法满足目前对于实时性要求较高的业务
-- mysql内部使用B+树,为了提高性能,会将表的索引装载到内存中,最终由于内存要求导致产生磁盘IO速度下降
-- 根据常用的说法,单表的数据量在2000万左右是上限,但考虑硬件配置,数据库参数设置,表结构设计和查询的业务复杂度,所以在这种场景下需求进行分库分表
-- 阿里规范手册给出的范围是单表500万条数据或者2GB单表容量后进行分库分表,是一个比较折中的范围
-- 由于表结构一样，所以目前考虑进行水平分库分表，减少开发难度，在更复杂的要求（高级）再考虑进行垂直分库分表

-- 分库分表策略：根据购买人ID和订单创建时间进行分库分表
-- 分库键：购买人user_id，分表键：订单创建时间create_time,以月或者年为单表分表
-- 因为买家和卖家在当前场景下都是查询我的订单,所以以user_id进行分库,可以保证同一个用户的数据避免出现跨库查询的问题,减少中间的数据聚合操作,不同买家
-- 的数据将被分散到不同的数据库,减少单个数据库的压力

-- 分库规则：根据购买人ID取模分库
-- 假设有N个数据库实例，则分库规则为：shard_db_index = user_id % N

-- 分表规则：根据订单创建时间进行分表，按月分表
-- 分表规则为：shard_table_index = YEAR(create_time)_MONTH(create_time), e.g: t_order_2024_04

-- 针对高级开发的后续优化方案思考:
-- 1.读写分离：针对不同的查询场景,可以实现读写分离,将读操作和写操作分别路由到不同的数据库实例上,这样可以避免读写冲突，提高系统并发能力;
-- 2.结合缓存：将查询结果缓存到内存中,减少数据库查询压力,提高查询性能。针对不同的场景，比如实时性要求高的查询场景,可以采用使用本地缓存,对于实时性稍低的可以采用分布式缓存;
-- 3.垂直分库分表：针对买家,卖家,客诉等不同维度进行垂直分库,可以减少查询的数据量,但多表联合查询会比较困难;
-- 4.考虑使用ElasticSearch,ES提供了快速的实时搜索和聚合功能,能够处理高并发的查询请求,并且可以通过优化索引和查询来提高查询性能和实时性;
-- 5.针对搜索客诉订单,可以考虑针对字段添加索引,因为分钟级响应对实时性要求不高;
-- 6.平台运营进行订单数据分析,如买家订单排行榜,卖家订单排行榜对于实时性要求不高.可以考虑按天为维度进行数据统计,通过数仓分层处理数据,并结合bi等组件进行展示.