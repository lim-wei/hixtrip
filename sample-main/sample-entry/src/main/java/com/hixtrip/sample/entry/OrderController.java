package com.hixtrip.sample.entry;

import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.app.api.PayStrategy;
import com.hixtrip.sample.app.service.PayStrategyFactory;
import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.client.order.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * todo 这是你要实现的
 */
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private PayStrategyFactory payStrategyFactory;

    /**
     * 下单接口
     *
     * @param request 入参对象
     * @return 新增订单西悉尼
     */
    @PostMapping(path = "/command/order/create")
    public OrderVO order(@RequestBody CommandOderCreateDTO request) {
        //登录信息可以在这里模拟
        var userId = "customer001";
        request.setUserId(userId);
        return orderService.createOrder(request);
    }

    /**
     * 【中、高级要求】需要使用策略模式处理至少三种场景：支付成功、支付失败、重复支付(自行设计回调报文进行重复判定)
     *
     * @param request 入参对象
     * @return 请修改出参对象
     */
    @PostMapping(path = "/command/order/pay/callback")
    public String payCallback(@RequestBody CommandPayDTO request) {
        PayStrategy strategy = payStrategyFactory.getStrategyByStatus(request);

        if (strategy == null) {
            return "未知支付状态，无法处理";
        }

        return strategy.handlePayResult(request);
    }

}
