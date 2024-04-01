package com.kafka.demo.controller;

import java.util.List;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kafka.demo.pojo.JwtRequest;
import com.kafka.demo.pojo.JwtResponse;
import com.kafka.demo.utils.JwtTokenUtil;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class MessageController {

	@Autowired
	Producer<String, String> producer;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

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

	@PostMapping("/authenticate")
	public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
	    // Authenticate the user using the authentication manager
	    Authentication authentication = authenticationManager.authenticate(
	        new UsernamePasswordAuthenticationToken(
	            authenticationRequest.getUsername(), authenticationRequest.getPassword()
	        )
	    );
	    
	    SecurityContextHolder.getContext().setAuthentication(authentication);

	    // Generate token
	    final String token = jwtTokenUtil.generateToken((UserDetails) authentication.getPrincipal());

	    // Return the token
	    return ResponseEntity.ok(new JwtResponse(token));
	}
	
	/*
	 * @GetMapping("/getusers") public ResponseEntity<List<String>> getUsers()
	 * throws Exception {
	 * 
	 * 
	 * return ResponseEntity.ok(new JwtResponse(token)); }
	 */

}