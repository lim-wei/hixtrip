package com.hixtrip.sample.app.service;

import com.hixtrip.sample.app.api.PayStrategy;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.pay.PayDomainService;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 支付成功策略
 *
 * @author linw
 * @create 2024/04/28
 **/
@Service("successStrategy")
@AllArgsConstructor
public class SuccessPayStrategy implements PayStrategy {

    private final OrderDomainService orderDomainService;
    private final PayDomainService payloadService;

    @Override
    public String handlePayResult(CommandPayDTO commandPayDTO) {
        CommandPay commandPay = CommandPay.builder()
                .orderId(commandPayDTO.getOrderId())
                .payStatus(commandPayDTO.getPayStatus())
                .build();
        orderDomainService.orderPaySuccess(commandPay);
        payloadService.payRecord(commandPay);
        return "支付成功处理逻辑执行完毕";
    }

}