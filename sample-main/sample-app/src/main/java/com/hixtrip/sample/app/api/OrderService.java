package com.hixtrip.sample.app.api;

import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.vo.OrderVO;

/**
 * 订单的service层
 */
public interface OrderService {

    /**
     * 创建订单
     *
     * @param request 新增请求
     * @return 订单VO类
     */
    OrderVO createOrder(CommandOderCreateDTO request);

}
