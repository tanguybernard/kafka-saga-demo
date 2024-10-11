package org.example.inventory.infra.driven;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.inventory.domain.OrderEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;


@Controller
public class InventoryController {

    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaOrderTemplate;

    @KafkaListener(topics = "placed-orders", groupId = "orders-group")
    public void processPayment(String event) throws JsonMappingException, JsonProcessingException, JsonProcessingException {

        System.out.println("YOLO inventory");
        System.out.println("Received event" + event);
        OrderEvent orderEvent = new ObjectMapper().readValue(event, OrderEvent.class);

        try {

            //TODO check if stocks in Inventory

            //simulate issue
            throw new Exception("Stock not available");



        } catch (Exception e) {

            // reverse previous task
            OrderEvent oe = new OrderEvent(orderEvent.id(), "ORDER_REVERSED_2");
            this.kafkaOrderTemplate.send("reversed-orders", oe);

        }

    }

}