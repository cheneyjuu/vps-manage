package com.swiftcode.service;

import com.swiftcode.config.ApplicationProperties;
import com.swiftcode.domain.Order;
import com.swiftcode.domain.Plan;
import com.swiftcode.domain.User;
import com.swiftcode.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户购买套餐
 *
 * @author chen
 */
@Service
public class PurchasePlanService {
    private final OrderRepository orderRepository;
    private final ApplicationProperties applicationProperties;

    @Autowired
    public PurchasePlanService(OrderRepository orderRepository,
                               ApplicationProperties applicationProperties) {
        this.orderRepository = orderRepository;
        this.applicationProperties = applicationProperties;
    }

    @Transactional(rollbackFor = Exception.class)
    public String buy(User user, Plan plan) {
        Order order = new Order();
        order.createOrder(user, plan);
        orderRepository.save(order);
        return applicationProperties.getPayment().getCodePath()
            + plan.getPrice()
            + applicationProperties.getPayment().getFormat();
    }
}
