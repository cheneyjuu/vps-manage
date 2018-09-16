package com.swiftcode.service;

import com.swiftcode.domain.Order;
import com.swiftcode.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 管理员确认订单.
 *
 * @author chen
 */
@Service
public class OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public void confirmPayed(String currentLogin) {
        Order order = orderRepository.findUserOrder(currentLogin).orElseThrow(IllegalAccessError::new);
        order.toPayed();
        orderRepository.save(order);
    }
}
