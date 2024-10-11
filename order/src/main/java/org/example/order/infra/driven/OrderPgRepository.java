package org.example.order.infra.driven;

import org.example.order.domain.Order;
import org.example.order.domain.OrderEvent;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public class OrderPgRepository {

    private final OrderDao dao;

    public OrderPgRepository(OrderDao dao) {

        this.dao = dao;
    }


    public Order save(Order order) {

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.id = order.id;
        orderEntity.dateDeCreation = LocalDate.now();
        orderEntity.status = order.status;

        dao.save(orderEntity);

        return order;
    }


    public Optional<Order> findById(String id) {
        Optional<OrderEntity> e = dao.findById(id);
        return e.map(entity -> new Order(entity.id, entity.status));
    }
}
