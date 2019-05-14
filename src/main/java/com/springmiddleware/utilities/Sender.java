package com.springmiddleware.utilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;



/**
 * Kafka generic sender
 * @author MLondei
 *
 */
@Service
public class Sender {



	/**
	 * From Apache Kafka docs: A template for executing high-level operations
	 */
	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;



	/**
	 * A string representing the topic for infos 
	 */
	@Value("${app.topic.info}")
	private String infoTopic;

	/**
	 * A string representing the topic for errors
	 */
	@Value("${app.topic.error}")
	private String errorTopic;


	/**
	 * Sends a message to topic "info"
	 * @param c the message/object to send
	 */
	public void sendInfo(Object c) {
		kafkaTemplate.send(infoTopic, c);
	}
	
	/**
	 * Sends a message to topic "error"
	 * @param c the message/object to send
	 */
	public void sendError(Object c) {
		kafkaTemplate.send(errorTopic, c);
	}

}
