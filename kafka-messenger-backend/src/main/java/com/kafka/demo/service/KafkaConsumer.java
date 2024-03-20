package com.kafka.demo.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @KafkaListener(topics = "test-topic", groupId = "demoConsumer")
    public void listen(String message) {
        System.out.println("Received Message: " + message);
    }
}