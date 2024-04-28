package com.hixtrip.sample.domain.order;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 订单领域服务
 */
@Component
public class OrderDomainService {

    @Autowired
    private OrderRepository orderRepository;

    /**
     * 创建待付款订单
     */
    public int createOrder(Order order) {
        return orderRepository.save(order);
    }

    /**
     * 判断是否为重复支付。
     *
     * @param orderId       订单ID
     * @param transactionId 交易ID
     * @return true 如果是重复支付，false 否
     */
    public boolean isDuplicatePayment(Long orderId, String transactionId) {
        // 查询订单是否已经存在相同的交易ID并且支付成功
        Optional<Order> existingOrder =
                Optional.of(orderRepository.findByIdAndTransactionId(orderId, transactionId));

        if (existingOrder.isPresent()) {
            // 订单已存在且交易ID匹配，进一步检查支付状态
            Order order = existingOrder.get();
            if (order.getPayStatus().equals("支付成功")) {
                // 已经支付过，可能是重复支付
                return true;
            }
        }

        // 如果没有找到匹配的记录或支付状态不是已支付，则不是重复支付
        return false;
    }

    /**
     * 待付款订单支付成功
     */
    public void orderPaySuccess(CommandPay commandPay) {
        Order order = orderRepository.findById(commandPay.getOrderId());
        if (null == order) {
            return;
        }
        order.successPayOrder();
        orderRepository.update(order);
    }

    /**
     * 待付款订单支付失败
     */
    public void orderPayFail(CommandPay commandPay) {
        Order order = orderRepository.findById(commandPay.getOrderId());
        if (null == order) {
            return;
        }
        order.failedPayOrder();
        orderRepository.update(order);
    }

    /**
     * 重复支付订单
     */
    public void orderPayDuplicate(CommandPay commandPay) {
        Order order = orderRepository.findById(commandPay.getOrderId());
        if (null == order) {
            return;
        }
        // 重复支付对订单不做修改
        order.duplicatePayOrder();
        orderRepository.update(order);
    }

}
