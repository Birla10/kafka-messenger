package com.kafka.demo.controller;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
//MessageController.java
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kafka.demo.service.KafkaConsumerService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/messages")
public class MessageController {

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;
	
	@Autowired 
	private KafkaConsumerService kafkaConsumerService;
	
	@PostMapping("/produce")
	public String produceMessage(@RequestBody String message) {
		Properties properties = new Properties();
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

		try (Producer<String, String> producer = new KafkaProducer<>(properties)) {
			ProducerRecord<String, String> record = new ProducerRecord<>("messenger", message);
			producer.send(record);
			consumeMessage();
			return "Message sent successfully!";
		} catch (Exception e) {
			return "Error sending message: " + e.getMessage();
		}
	}

    //@GetMapping("/consume")
	private String consumeMessage() throws KafkaException{
		
		Properties properties = new Properties();
		properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "test-consumer-group");
		properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		
		
		try (Consumer<String, String> consumer = new KafkaConsumer<>(properties)) {
			consumer.subscribe(Collections.singletonList("messenger"));

			ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(10000));
			if (records.isEmpty()) {
				return "No messages available.";
			} else {
				System.out.println(records);
				kafkaConsumerService.onMessageReceived(records.iterator().next().value());
				return "Received message: " + records.iterator().next().value();
			}
		} catch (Exception e) {
			return "Error consuming message: " + e.getMessage();
		}
	}
}
