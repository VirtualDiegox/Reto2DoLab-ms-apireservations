package com.reto2.microservices.apireservations.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @NotBlank(message = "bikeId is required")
    private String bikeId;
    @NotBlank(message = "created is required")
    private Date created;

    public Reservation(String bikeId,Date created) {
        this.bikeId = bikeId;
        this.created = created;
    }
    
}
