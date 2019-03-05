package com.fettuccine.entity;

import java.util.List;

/**
 * @author rlosard
 *
 */
public class User {
	private String id;
	private String name;
	private String description;
	private List<Document> documents;

	/**
	 * @param id
	 * @param name
	 * @param description
	 * @param documents
	 */
	public User(String id, String name, String description, List<Document> documents) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.documents = documents;
	}

	/**
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return
	 */
	public List<Document> getDocuments() {
		return documents;
	}

	/**
	 * @param documents
	 */
	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}

	@Override
	public String toString() {
		return String.format("User [id=%s, name=%s, description=%s, documents=%s]", id, name, description, documents);
	}
}