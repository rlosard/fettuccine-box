package com.fettuccine.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fettuccine.entity.Document;
import com.fettuccine.entity.User;
import com.fettuccine.service.UserService;

/**
 * @author rlosard
 *
 */
@RestController
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * @param userId
	 * @return
	 */
	@GetMapping("/users/{userId}/documents")
	public List<Document> retrieveDocumentsByUser(@PathVariable String userId) {
		return userService.retrieveDocuments(userId);
	}
	
	
	/**
	 * @param userId
	 * @return
	 */
	@GetMapping("/users")
	public List<User> retrieveUsers() {
		return userService.retrieveAllUsers();
	}
	
	
	
	/**
	 * @param userId
	 * @param documentId
	 * @return
	 */
	@GetMapping("/users/{userId}/documents/{documentId}")
	public Document retrieveDocument(@PathVariable String userId, @PathVariable String documentId) {
		return userService.retrieveDocument(userId, documentId);
		
		
	}
	

	/**
	 * @param userId
	 * @param newDocument
	 * @return
	 */
	@PostMapping("/users/{userId}/documents")
	public ResponseEntity<Void> registerUserForDocument(@PathVariable String userId,
			@RequestBody Document newDocument) {

		Document document = userService.addDocument(userId, newDocument);

		if (document == null)
			return ResponseEntity.noContent().build();

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(document.getId())
				.toUri();

		return ResponseEntity.created(location).build();
	}

}
