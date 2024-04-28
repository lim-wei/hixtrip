package com.hixtrip.sample.domain.pay.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommandPay {

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 订单状态
     */
    private String payStatus;

    /**
     * 记录支付结果
     */
    public void recordPaymentResult() {
        // 验证支付状态是否合法，比如是否已经支付过
        if (this.isAlreadyPaid()) {
            throw new IllegalStateException("订单已支付，不能重复记录");
        }

        // 执行支付结果的持久化操作
//        paymentRepository.save(this);
    }

    private boolean isAlreadyPaid() {
        // 实现逻辑，检查订单是否已支付
        return false;
    }

}