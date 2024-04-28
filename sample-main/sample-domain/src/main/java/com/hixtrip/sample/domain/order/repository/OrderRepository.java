package com.hixtrip.sample.domain.order.repository;

import com.hixtrip.sample.domain.order.model.Order;

/**
 *
 */
public interface OrderRepository {

    /**
     * 根据id获取订单
     *
     * @param id 订单ID
     * @return 订单表领域对象
     */
    Order findById(Long id);

    /**
     * 根据id和交易号获取订单
     *
     * @param id 订单ID
     * @param transactionId 交易ID
     * @return
     */
    Order findByIdAndTransactionId(Long id, String transactionId);

    /**
     * 保存订单
     *
     * @param order 订单表领域对象
     * @return 新增条数
     */

    int save(Order order);

    /**
     * 更新订单
     *
     * @param order 订单表领域对象
     * @return 更新条数
     */
    int update(Order order);

}
