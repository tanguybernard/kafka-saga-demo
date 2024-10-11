package org.example.order.domain;

public class Order {

    public final String id;
    public String status;

    public Order(String id, String status){
        this.id = id;
        this.status = status;
    }

}
