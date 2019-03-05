package com.fettuccine.service;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fettuccine.entity.Document;
import com.fettuccine.entity.User;

@Component
public class UserService {

	private static List<User> users = new ArrayList<>();

	static {
		// Initialize Data
		Document document1 = new Document("Document1", "doc1", "Bla bla bla", "20190305", "docx");
		Document document2 = new Document("Document2", "doc2", "Bla bla bla", "20190305", "docx");
		Document document3 = new Document("Document3", "doc3", "Bla bla bla", "20190305", "docx");
		Document document4 = new Document("Document4", "doc4", "Bla bla bla", "20190305", "docx");

		User user1 = new User("User1", "Aurelia M", "Runner, Developer",
				new ArrayList<>(Arrays.asList(document1, document2, document3, document4)));

		User user2 = new User("User2", "Ruben LS", "Hiker, Pilot, IT Geek",
				new ArrayList<>(Arrays.asList(document1, document2, document3, document4)));

		users.add(user1);
		users.add(user2);
	}

	public List<User> retrieveAllUsers() {
		return users;
	}

	/**
	 * @param userId
	 * @return
	 */
	public User retrieveUser(String userId) {
		for (User user : users) {
			if (user.getId().equals(userId)) {
				return user;
			}
		}
		return null;
	}

	/**
	 * @param userId
	 * @return
	 */
	public List<Document> retrieveDocuments(String userId) {
		User user = retrieveUser(userId);

		if (user == null) {
			return null;
		}

		return user.getDocuments();
	}

	/**
	 * @param userId
	 * @param documentId
	 * @return
	 */
	public Document retrieveDocument(String userId, String documentId) {
		User user = retrieveUser(userId);

		if (user == null) {
			return null;
		}

		for (Document document : user.getDocuments()) {
			if (document.getId().equals(documentId)) {
				return document;
			}
		}

		return null;
	}

	private SecureRandom random = new SecureRandom();

	/**
	 * @param userId
	 * @param document
	 * @return
	 */
	public Document addDocument(String userId, Document document) {
		User user = retrieveUser(userId);

		if (user == null) {
			return null;
		}

		String randomId = new BigInteger(130, random).toString(32);
		document.setId(randomId);

		user.getDocuments().add(document);
		return document;
	}
}