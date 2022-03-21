package com.reto2.microservices.apireservations.controller;

import java.util.List;

import com.reto2.microservices.apireservations.dto.EndReservationDTO;
import com.reto2.microservices.apireservations.dto.ReservationDTO;
import com.reto2.microservices.apireservations.services.RabbitMQSender;
import com.reto2.microservices.apireservations.services.ReservationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class ReservationsController {

    private final ReservationService reservationService;
    @Autowired
	RabbitMQSender rabbitMQSender;

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

    @GetMapping("/reservations/{id}")
    public ResponseEntity<ReservationDTO> getReservationById(@PathVariable("id") String id) {
        try {
            ReservationDTO reservation = reservationService.getByBikeId(id);
            return new ResponseEntity<>(reservation, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        } 
    }

    @PutMapping("/reservations/{id}")
    public ResponseEntity<ReservationDTO> updateBike(@PathVariable("id") String id, @RequestBody ReservationDTO bike) {
        try {
            ReservationDTO updatedReservation = reservationService.update(id, bike);
            return new ResponseEntity<>(updatedReservation, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }  
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationDTO> createReservation(@RequestBody ReservationDTO newReservation) {
        try {
            ReservationDTO reservation = reservationService.create(newReservation);
            rabbitMQSender.createReservation(reservation);
            return new ResponseEntity<>(reservation, HttpStatus.OK);    
        } catch (Exception e) {
            System.out.println("Error creating");
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<HttpStatus> endReservation(@PathVariable("id") String id) {
        try {
            ReservationDTO reservation = reservationService.getByBikeId(id);
            EndReservationDTO end = new EndReservationDTO(reservation.getBikeId());
            reservationService.delete(id);
            rabbitMQSender.endReservation(end);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/reservations")
    public ResponseEntity<HttpStatus> deleteAllReservations() {
        try {
            List<ReservationDTO> reservations = reservationService.getAll();
            for( ReservationDTO r : reservations ){
                EndReservationDTO end = new EndReservationDTO(r.getBikeId());
                rabbitMQSender.endReservation(end);
            }
            reservationService.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
