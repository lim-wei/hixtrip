package com.hixtrip.sample.domain.order.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 订单表领域对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@SuperBuilder(toBuilder = true)
public class Order {

    /**
     * 订单号
     */
    private Long id;

    /**
     * 购买人
     */
    private String userId;

    /**
     * SkuId
     */
    private String skuId;

    /**
     * 订单号
     */
    private String transactionId;

    /**
     * 购买数量
     */
    private Integer amount;

    /**
     * 购买金额
     */
    private BigDecimal money;

    /**
     * 购买时间
     */
    private LocalDateTime payTime;

    /**
     * 支付状态
     */
    private String payStatus;

    /**
     * 删除标志（0代表存在 1代表删除）
     */
    private Integer delFlag;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改人
     */
    private String updateBy;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 构建保存对象,公共字段处理
     */
    public Order buildSave() {
        // 此处仅模拟
        this.setId(System.currentTimeMillis());
        this.setTransactionId(UUID.randomUUID().toString());
        this.setCreateTime(LocalDateTime.now());
        this.setUpdateTime(LocalDateTime.now());
        this.setDelFlag(0);
        this.setCreateBy("creator");
        this.setUpdateBy("creator");
        this.setPayStatus("未支付");
        return this;
    }

    /**
     * 支付成功
     */
    public void successPayOrder() {
        this.setUpdateTime(LocalDateTime.now());
        this.setUpdateBy("updater");
        this.setPayStatus("支付成功");
    }

    /**
     * 支付失败
     */
    public void failedPayOrder() {
        this.setUpdateTime(LocalDateTime.now());
        this.setUpdateBy("updater");
        this.setPayStatus("支付失败");
    }

    /**
     * 重复支付
     */
    public void duplicatePayOrder() {
        this.setUpdateTime(LocalDateTime.now());
        this.setUpdateBy("updater");
    }

}
