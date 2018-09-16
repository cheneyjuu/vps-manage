package com.swiftcode.web.rest;

import com.swiftcode.security.AuthoritiesConstants;
import com.swiftcode.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
