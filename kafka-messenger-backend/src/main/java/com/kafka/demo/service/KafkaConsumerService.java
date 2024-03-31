package com.kafka.demo.service;

import java.time.Duration;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@EnableAsync
@Service
public class KafkaConsumerService {

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;

	@Autowired
	private SimpMessagingTemplate template;

	@Autowired
	Consumer<String, String> consumer;

	@PostConstruct
	private void consumeMessage() throws KafkaException {

		try {
			Thread pollThread = new Thread(() -> {
				while (true) {
					
					ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofMillis(10));
					consumerRecords.forEach(record -> {
						System.out.println(record.value());
						template.convertAndSend("/topic/messages", record.value());
					});
					consumer.commitSync();
				}
			});
			pollThread.start();
			
		} catch (Exception e) {
			System.out.println( "Error consuming message: " + e.getMessage());
		}
		
	}
}
