package com.hixtrip.sample.infra;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.infra.db.convertor.OrderDOConvertor;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;
import com.hixtrip.sample.infra.db.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 处理订单相关接口的具体实现
 *
 * @author linw
 * @create 2024/04/22
 **/
@Component
public class OrderRepositoryImpl implements OrderRepository {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Order findById(Long id) {
        OrderDO orderDO = orderMapper.selectById(id);
        return OrderDOConvertor.INSTANCE.doToDomain(orderDO);
    }

    @Override
    public Order findByIdAndTransactionId(Long id, String transactionId) {
        Wrapper<OrderDO> wrapper = Wrappers.<OrderDO>query().lambda()
                .eq(OrderDO::getId, id)
                .eq(OrderDO::getTransactionId, transactionId);
        OrderDO orderDO = orderMapper.selectOne(wrapper);
        return OrderDOConvertor.INSTANCE.doToDomain(orderDO);
    }

    @Override
    public int save(Order order) {
        return orderMapper.insert(OrderDO.of(order));
    }

    @Override
    public int update(Order order) {
        if (null == order) {
            return 0;
        }
        return orderMapper.updateById(OrderDO.of(order));
    }

}
