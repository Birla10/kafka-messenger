package com.kafka.demo.service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

@Service
public class XmlFileUserDetailsService implements UserDetailsService {

	@Value("${users.xmlFilePath}")
	private String xmlFilePath;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(Files.newInputStream(Paths.get(xmlFilePath)));
			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("user");

			for (int i = 0; i < nList.getLength(); i++) {

				Element userElement = (Element) nList.item(i);
				String xmlUsername = userElement.getElementsByTagName("username").item(0).getTextContent();

				if (xmlUsername.equals(username)) {
					String xmlPassword = userElement.getElementsByTagName("password").item(0).getTextContent();
					String xmlRoles = userElement.getElementsByTagName("roles").item(0).getTextContent();

					return User.withUsername(username).password(xmlPassword).roles(xmlRoles.split(",")) // Assuming
																										// roles are
																										// comma
																										// separated
							.build();
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Error reading from XML file", e);
		}

		throw new UsernameNotFoundException("User not found with username: " + username);
	}

}
