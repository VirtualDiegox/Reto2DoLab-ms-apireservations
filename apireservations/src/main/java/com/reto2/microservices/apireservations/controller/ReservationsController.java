package com.reto2.microservices.apireservations.controller;

import java.util.List;

import com.reto2.microservices.apireservations.dto.ReservationDTO;
import com.reto2.microservices.apireservations.services.ReservationService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class ReservationsController {

    private final ReservationService reservationService;

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationDTO>> retrieveReservations(){
        try {
            List<ReservationDTO> reservations = reservationService.getAll();
            return new ResponseEntity<>(reservations, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        } 
    }
    @PostMapping("/reservations")
    public ResponseEntity<ReservationDTO> createReservation(@RequestBody ReservationDTO newReservation) {
        try {
            ReservationDTO reservation = reservationService.create(newReservation);
            return new ResponseEntity<>(reservation, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error creating");
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
