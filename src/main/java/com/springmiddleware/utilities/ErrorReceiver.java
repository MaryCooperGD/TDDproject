package com.springmiddleware.utilities;

import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.springmiddleware.entities.Data;



/**
 * 
 * Kafka receiver for error messages.
 * @author MLondei
 *
 */
@KafkaListener(topics = "${app.topic.error}")
@Service
public class ErrorReceiver {



	/**
	 * Handles the receiving of a Data message
	 * @param message the received message of type Data
	 */
	@KafkaHandler
	public void listen(@Payload Data message) {
		System.out.println("[ERROR] " + message);
	}



	/**
	 * Handles the receiving of a String message
	 * @param message the received message of type String
	 */
	@KafkaHandler
	public void listen(@Payload String message) {
		System.out.println("[ERROR] " + message);
	}

}
