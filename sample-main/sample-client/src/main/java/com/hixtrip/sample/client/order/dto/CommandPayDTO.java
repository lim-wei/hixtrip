package com.hixtrip.sample.client.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 支付回调的入参
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommandPayDTO {

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 支付状态
     * 后期定义枚举
     */
    private String payStatus;

    /**
     * 交易ID
     * 判断是否重复支付
     */
    private String transactionId;

}
