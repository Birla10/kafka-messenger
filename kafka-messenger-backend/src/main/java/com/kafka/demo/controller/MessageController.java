package com.kafka.demo.controller;

import java.util.UUID;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kafka.demo.pojo.UserDetails;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class MessageController {


	@Autowired
	Producer<String, String> producer;
	
	@PostMapping("/produce")
	public String produceMessage(@RequestBody String message) {
		try {
			ProducerRecord<String, String> record = new ProducerRecord<>("messenger", message);
			producer.send(record);
			System.out.println("Message sent successfully!");
			return "Message sent successfully!";
		} catch (Exception e) {
			return "Error sending message: " + e.getMessage();
		}
	}
	
	/*
	 * @PostMapping("/login") public String authenticateLogin(@RequestBody
	 * UserDetails userDetails) {
	 * 
	 * 
	 * UUID uuid = UUID.randomUUID(); System.out.println(uuid.toString()); return
	 * uuid.toString();
	 * 
	 * }
	 */

   
}