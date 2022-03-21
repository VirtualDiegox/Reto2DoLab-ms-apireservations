package com.reto2.microservices.apireservations.repository;

import java.util.Optional;

import com.reto2.microservices.apireservations.model.Reservation;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, String>{
    Optional<Reservation> findByBikeId(String id);
    void deleteByBikeId(String id);
}
