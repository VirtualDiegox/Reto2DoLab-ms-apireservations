package com.reto2.microservices.apireservations.services;
import com.reto2.microservices.apireservations.dto.EndReservationDTO;
import com.reto2.microservices.apireservations.dto.ReservationDTO;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {
	
	@Autowired
	private RabbitTemplate template;
	
	@Value("${reto2.microservices.apireservations.rabbitmq.exchange}")
	private String exchange;
	
	@Value("${reto2.microservices.apireservations.rabbitmq.routingkey}")
	private String routingkey;	
	
	public void createReservation(ReservationDTO reservation) {
		template.convertAndSend(exchange, routingkey, reservation);
		System.out.println("Send msg = " + reservation); 
	}
    public void endReservation(EndReservationDTO endreservation) {
		template.convertAndSend(exchange, routingkey, endreservation);
		System.out.println("Send msg = " + endreservation); 
	}

}
