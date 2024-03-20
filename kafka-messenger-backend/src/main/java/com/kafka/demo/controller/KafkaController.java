package com.kafka.demo.controller;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "test-topic";

    public KafkaController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @GetMapping("/publish/{message}")
    public String sendMessageToKafkaTopic(@PathVariable String message) {
    	boolean isMessageSent = false;
        try {
        	kafkaTemplate.send(TOPIC, message);
        	isMessageSent = true;
        } catch (Exception e) {
        	e.printStackTrace();
        }
    	if(isMessageSent) {
    		return "Message sent to Kafka topic successfully!";
    	}
    	else {
    		return "Error in sending message";
    	}
    }
}

