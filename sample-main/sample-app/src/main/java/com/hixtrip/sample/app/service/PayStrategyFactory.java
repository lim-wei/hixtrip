package com.hixtrip.sample.app.service;

import com.hixtrip.sample.app.api.PayStrategy;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.domain.order.OrderDomainService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 策略工厂
 *
 * @author linw
 * @create 2024/04/28
 **/
@Service
@AllArgsConstructor
public class PayStrategyFactory {

    private final OrderDomainService orderDomainService;

    private final Map<String, PayStrategy> strategyMap;

    /**
     * 策略映射
     *
     * @param request 支付请求
     * @return 对应支付策略实现
     */
    public PayStrategy getStrategyByStatus(CommandPayDTO request) {
        boolean isDuplicate =
                orderDomainService.isDuplicatePayment(request.getOrderId(), request.getTransactionId());
        String payStatus;
        if (isDuplicate) {
            payStatus = "重复支付";
        } else {
            payStatus = request.getPayStatus();
        }
        return strategyMap.get(payStatus);
    }

}