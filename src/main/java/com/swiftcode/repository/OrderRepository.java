package com.swiftcode.repository;

import com.swiftcode.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author chen
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findTopByLoginAndIsDisableOrderByCreatedDateDesc(String login, Integer isDisable);

    List<Order> findByStatusAndIsDisable(Integer status, Integer isDisable);

    default Optional<Order> findUserOrder(String login) {
        return findTopByLoginAndIsDisableOrderByCreatedDateDesc(login, 0);
    }

    default List<Order> findToBeConfirmOrders() {
        return findByStatusAndIsDisable(1, 0);
    }

    default List<Order> findPayedOrders() {
        return findByStatusAndIsDisable(2, 0);
    }
}
