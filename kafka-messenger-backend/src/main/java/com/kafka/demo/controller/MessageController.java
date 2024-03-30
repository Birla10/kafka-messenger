package com.kafka.demo.controller;

import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
//MessageController.java
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/messages")
public class MessageController {


	@Autowired
	Producer<String, String> producer;
	
	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;
	
	@PostMapping("/produce")
	public String produceMessage(@RequestBody String message) {
		try {
			ProducerRecord<String, String> record = new ProducerRecord<>("messenger", message);
			producer.send(record);
			return "Message sent successfully!";
		} catch (Exception e) {
			return "Error sending message: " + e.getMessage();
		}
	}

   
}