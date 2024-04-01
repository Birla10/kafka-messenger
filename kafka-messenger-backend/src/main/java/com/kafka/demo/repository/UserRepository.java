package com.kafka.demo.repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

@Repository
public class UserRepository {

	@Value("${users.xmlFilePath}")
	private String xmlFilePath;

	
	public List<String> loadUsersFromXML() {
		List<String> usersList = new ArrayList<>();
		try {
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(Files.newInputStream(Paths.get(xmlFilePath)));
			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("user");

			for (int i = 0; i < nList.getLength(); i++) {
				Element userElement = (Element) nList.item(i);
				String xmlUsername = userElement.getElementsByTagName("username").item(0).getTextContent();
				usersList.add(xmlUsername);
			}
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return usersList;

	}
}
