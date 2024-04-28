package com.hixtrip.sample.infra.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单表Mapper层
 *
 * @author linw
 * @create 2024/04/22
 **/
@Mapper
public interface OrderMapper extends BaseMapper<OrderDO> {
}
