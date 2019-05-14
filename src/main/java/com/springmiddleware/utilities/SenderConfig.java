package com.springmiddleware.utilities;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

/**
 * Configuration class for the Kafka sender.
 * @author MLondei
 *
 */
@Configuration
public class SenderConfig {

	
	/**
	 * Represents the address of the bootstrap server
	 */
	@Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    /**
     * Sets the properties that will be used for the Kafka sender
     * @return the properties specified
     */
    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return props;
    }

    /**
     * From Apache Kafka docs: creates a producer factory that represents the strategy to producer a Producer instance
     * @return the producer factory
     */
    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    } 
    

    /**
     * A template for executing high-level operations in Kafka. Created using the {@link #producerFactory()}
     * @return tha kafka template
     */
    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

}
