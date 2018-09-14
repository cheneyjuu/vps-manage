package com.swiftcode.repository;

import com.swiftcode.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author chen
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
}
