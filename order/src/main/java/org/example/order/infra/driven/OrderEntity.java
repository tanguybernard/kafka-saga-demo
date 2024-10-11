package org.example.order.infra.driven;



import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class OrderEntity {

    @Id
    public String id;

    public LocalDate dateDeCreation;

    public String status;

}