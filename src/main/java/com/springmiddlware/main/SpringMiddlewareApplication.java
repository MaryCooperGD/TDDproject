package com.springmiddlware.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;



/**
 * 
 * Main class of the program.
 * 
 * @author MLondei
 *
 */
@SpringBootApplication(scanBasePackages = "com.springmiddleware")
@ComponentScan("com.springmiddleware")
@EnableAutoConfiguration
@EntityScan("com.springmiddleware")
@EnableJpaRepositories("com.springmiddleware.repository")
public class SpringMiddlewareApplication /*implements CommandLineRunner */{



	public static void main(String[] args) throws Exception {
		SpringApplication.run(SpringMiddlewareApplication.class, args);

	}


/*
	@Override
	public void run(String... strings) throws Exception {
		
		
		Process p = new ProcessBuilder("C:\\kafka_2.12-2.2.0\\bin\\windows\\zookeeper-server-start.bat",
				"C:\\kafka_2.12-2.2.0\\config\\zookeeper.properties").start();
		
		if(p == null) {
			System.out.println("P is null");
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line = null;
		System.out.println("P id "+p.pid());
		System.out.println(""+p.isAlive());
		while ((line = reader.readLine()) != null) {
			System.out.println("Reading");
		}
		
		

		/*Process p1 = new ProcessBuilder("C:\\kafka_2.12-2.2.0\\bin\\windows\\kafka-server-start.bat",
				"C:\\kafka_2.12-2.2.0\\config\\server.properties").start();*/

		
		//}
		
		/*
		 * prop.setProperty("dataDir",
		 * "C:\\kafka_2.12-2.2.0\\config\\zookeeper.properties");
		 * prop.setProperty("bootstrap.servers", "localhost:2181"); QuorumPeerConfig
		 * quorumConfiguration = new QuorumPeerConfig(); try {
		 * quorumConfiguration.parseProperties(prop); } catch(Exception e) { throw new
		 * RuntimeException(e); }
		 * 
		 * ZooKeeperServerMain zookeeper = new ZooKeeperServerMain(); final ServerConfig
		 * configuration = new ServerConfig();
		 * configuration.readFrom(quorumConfiguration);
		 * 
		 * new Thread() { public void run() { try {
		 * zookeeper.runFromConfig(configuration); } catch (IOException e) {
		 * 
		 * } } }.start();
		 * 
		 * 
		 * Properties props = new Properties(); props.put("bootstrap.servers",
		 * "localhost:9092"); props.put("acks", "all"); props.put("retries", 0);
		 * props.put("batch.size", 16384); props.put("linger.ms", 1);
		 * props.put("buffer.memory", 33554432); props.put("key.serializer",
		 * "org.apache.kafka.common.serialization.StringSerializer");
		 * props.put("value.serializer",
		 * "org.apache.kafka.common.serialization.StringSerializer");
		 * 
		 * KafkaProducer<String, String> producer = new KafkaProducer<String,
		 * String>(props); for (int i = 0; i < 100; i++) producer.send(new
		 * ProducerRecord<String, String>("info", Integer.toString(i),
		 * Integer.toString(i)));
		 * 
		 * producer.close();
		 */



}
