package com.reto2.microservices.apireservations.services;

import java.util.ArrayList;
import java.util.List;

import com.reto2.microservices.apireservations.dto.ReservationDTO;
import com.reto2.microservices.apireservations.model.Reservation;
import com.reto2.microservices.apireservations.repository.ReservationRepository;

import org.springframework.beans.factory.annotation.Autowired;

public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    public ReservationDTO create(ReservationDTO reservationDTO){
        try {
            Reservation r = reservationRepository.save(new Reservation(
                                                        reservationDTO.getBikeId(),
                                                        reservationDTO.getCreated()));
            ReservationDTO newReservationDTO = new ReservationDTO(
                r.getBikeId(),
                r.getCreated()
            );
            return newReservationDTO;
        } catch (Exception e) {
            throw e;
        }
    }

    public List<ReservationDTO> getAll(){
        try {
            List<ReservationDTO> reservations = new ArrayList<>();
            for( Reservation r : reservationRepository.findAll() ){
                reservations.add( new ReservationDTO( r.getBikeId(),
                                                      r.getCreated()));
            }
            return reservations;
        } catch (Exception e) {
            throw e;
        }
    }

    public void delete(String id){
        try {
            reservationRepository.deleteByBikeId(id);
        } catch (Exception e) {
            throw e;
        }
    }

    public void deleteAll(){
        try {
            reservationRepository.deleteAll();
        } catch (Exception e) {
            throw e;
        }
    }
}
