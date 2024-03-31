package com.kafka.demo.service;

import java.time.Duration;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

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
	public void start() {

		Thread thread = new Thread(this::pollKafka);
		thread.start();
	}

	private void pollKafka() {
		try {
			while (true) {
				ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
				records.forEach(record -> {
					System.out.println(record.value());
					template.convertAndSend("/topic/messages", record.value());
				});
				consumer.commitSync();
			}
		} catch (Exception e) {
			System.out.println("Error consuming message: " + e.getMessage());
		} finally {
			consumer.close();
		}
	}

	@PreDestroy
	public void stop() {
		if (consumer != null) {
			consumer.wakeup();
		}
	}

}
