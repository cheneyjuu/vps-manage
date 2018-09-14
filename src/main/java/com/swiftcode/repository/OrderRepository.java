package com.swiftcode.repository;

import com.swiftcode.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author chen
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByUserInfoLogin(String login);
}
