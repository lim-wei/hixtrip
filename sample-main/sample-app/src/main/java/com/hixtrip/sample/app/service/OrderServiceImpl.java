package com.hixtrip.sample.app.service;

import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.app.convertor.OrderConvertor;
import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.vo.OrderVO;
import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.order.model.Order;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * app层负责处理request请求，调用领域服务
 */
@Component
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderDomainService orderDomainService;
    private final InventoryDomainService inventoryDomainService;

    @Override
    public OrderVO createOrder(CommandOderCreateDTO request) {
        Integer inventory = inventoryDomainService.getInventory(request.getSkuId());
        if (inventory <= 0) {
            throw new RuntimeException("库存数量不足,无法购买");
        }

        // 创建新增订单对象
        Order order = Order.builder()
                .skuId(request.getSkuId())
                .userId(request.getUserId())
                .amount(request.getAmount())
                .money(request.getMoney())
                .payTime(request.getPayTime())
                .payStatus(request.getPayStatus())
                .build();
        order.buildSave();
        // 调用领域服务,创建待付款订单
        orderDomainService.createOrder(order);
        return OrderConvertor.INSTANCE.OrderToOrderVO(order);
    }

}
