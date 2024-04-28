package com.hixtrip.sample.client.sample.vo;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 这是返回值的示例
 */
@Data
@Builder
public class SampleVO {

    /**
     * 订单号
     */
    private String id;

    /**
     * 购买人
     */
    private String userId;

    /**
     * SkuId
     */
    private String skuId;

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

}
