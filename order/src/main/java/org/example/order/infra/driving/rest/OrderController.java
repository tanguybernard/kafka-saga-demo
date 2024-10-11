package org.example.order.infra.driving.rest;

import org.example.order.domain.Order;
import org.example.order.domain.OrderEvent;
import org.example.order.infra.driven.OrderPgRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    private OrderPgRepository repository;

    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;


    /**
     * PAsser la commande
     *
     */
    @PostMapping("/orders")
    public void placeOrder(@RequestBody PlaceOrderRequest placeOrderRequest) {

        Order order = new Order(placeOrderRequest.id(), "ORDER_PLACED");
        try {
            order = this.repository.save(order);

            System.out.println("ORDER SAved database");


            // publish order created event for inventory microservice to consume.

            OrderEvent event = new OrderEvent(order.id, "ORDER_PLACED");
            this.kafkaTemplate.send("placed-orders", event);
        } catch (Exception e) {

            System.out.println("ISSUE:"+e.getMessage());


            order.status = "FAILED";
            this.repository.save(order);

        }

    }
}








