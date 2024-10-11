package org.example.order.infra.driving.messaging;

import java.util.Optional;

import org.example.order.domain.Order;
import org.example.order.domain.OrderEvent;
import org.example.order.infra.driven.OrderPgRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ReverseOrderController {

    @Autowired
    private OrderPgRepository repository;

    @KafkaListener(topics = "reversed-orders", groupId = "orders-group")
    public void reverseOrder(String event) {

        try {

            System.out.println("Reverse order: " +event);


            OrderEvent orderEvent = new ObjectMapper().readValue(event, OrderEvent.class);

            Optional<Order> order = this.repository.findById(orderEvent.id());
            order.ifPresent(o -> {
                o.status = "FAILED";
                this.repository.save(o);
            });
        } catch (Exception e) {

            e.printStackTrace();
        }

    }
}