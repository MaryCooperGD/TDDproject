package com.springmiddleware.utilities;

import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.springmiddleware.entities.Data;



/**
 * Kafka receiver for info messages
 * @author MLondei
 *
 */
@KafkaListener(topics = "${app.topic.info}")
@Service
public class InfoReceiver {



	// private CountDownLatch latch = new CountDownLatch(1);



	/*
	 * public CountDownLatch getLatch() { return latch; }
	 */



	/**
	 * Handles the receiving of Data messages
	 * @param message the received message of type Data
	 */
	@KafkaHandler
	public void listen(@Payload Data message) {
		System.out.println("[INFO] " + message);
	}



	/**
	 * Handles the receiving of String messages
	 * @param message the received message of type String
	 */
	@KafkaHandler
	public void listen(@Payload String message) {
		System.out.println("[INFO] " +message);
	}


	/**
	 * Handles the receiving of generic messages
	 * @param obj the received generic message
	 */
	@KafkaHandler(isDefault = true)
	public void listen(Object obj) {
		ObjectMapper m = new ObjectMapper();
		Data c = m.convertValue(obj, Data.class);
		System.out.println("[INFO] Data" + c.toString());
	}

}
