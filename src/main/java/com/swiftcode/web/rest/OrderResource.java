package com.swiftcode.web.rest;

import com.google.common.collect.Lists;
import com.swiftcode.domain.Order;
import com.swiftcode.security.AuthoritiesConstants;
import com.swiftcode.service.OrderService;
import com.swiftcode.web.rest.vm.OrderVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author chen
 */
@RestController
@RequestMapping(value = "/api")
public class OrderResource {
    private final OrderService orderService;

    @Autowired
    public OrderResource(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders/confirm")
    public ResponseEntity<List<OrderVM>> toBeConfirmOrders() {
        List<Order> toBeConfirmOrders = orderService.toBeConfirmOrders();
        List<OrderVM> vmList = Lists.newArrayListWithCapacity(toBeConfirmOrders.size());
        for (Order order : toBeConfirmOrders) {
            vmList.add(OrderVM.entityToVM(order));
        }
        return ResponseEntity.ok(vmList);
    }

    @GetMapping("/orders/payed")
    public ResponseEntity<List<OrderVM>> payedOrders() {
        List<Order> toBeConfirmOrders = orderService.payedOrders();
        List<OrderVM> vmList = Lists.newArrayListWithCapacity(toBeConfirmOrders.size());
        for (Order order : toBeConfirmOrders) {
            vmList.add(OrderVM.entityToVM(order));
        }
        return ResponseEntity.ok(vmList);
    }

    /**
     * 管理员确认订单.
     *
     * @param params 被确认用户的登录名
     * @return 200 (OK)
     */
    @PutMapping("/orders/confirm")
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<Void> confirmOrder(@RequestBody Map<String, String> params) {
        String login = params.get("login");
        Objects.requireNonNull(login, "user login name can not be null");
        orderService.confirmPayed(login);
        return ResponseEntity.ok().body(null);
    }
}
