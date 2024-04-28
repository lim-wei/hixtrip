package com.hixtrip.sample.app.service;

import com.hixtrip.sample.app.api.PayStrategy;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 重复支付策略
 *
 * @author linw
 * @create 2024/04/28
 **/
@Service("duplicateStrategy")
@AllArgsConstructor
public class DuplicatePayStrategy implements PayStrategy {

    private final OrderDomainService orderDomainService;

    @Override
    public String handlePayResult(CommandPayDTO commandPayDTO) {
        CommandPay commandPay = CommandPay.builder()
                .orderId(commandPayDTO.getOrderId())
                .payStatus(commandPayDTO.getPayStatus())
                .build();
        orderDomainService.orderPayDuplicate(commandPay);
        return "重复支付处理逻辑执行完毕";
    }

}