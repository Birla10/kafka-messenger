package com.kafka.demo.service;

import java.time.Duration;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.demo.pojo.ChatMessage;

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
			ObjectMapper mapper = new ObjectMapper();

			while (true) {
				ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
				records.forEach(record -> {
					try {
						JSONObject jsonObject = new JSONObject(record.value());
						String jsonStr = jsonObject.getString("body");
						ChatMessage message = mapper.readValue(jsonStr, ChatMessage.class);
						System.out.println(message.getMessage());
						template.convertAndSendToUser(message.getSender(),"/queue/messages", message.getMessage());
						System.out.println("published to websocket "+ message.getSender());
					} catch (JsonProcessingException e) {
						System.out.println("Error consuming message: " + e.getMessage());
					}
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
