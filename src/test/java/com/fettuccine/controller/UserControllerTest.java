package com.fettuccine.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fettuccine.controller.UserController;
import com.fettuccine.entity.Document;
import com.fettuccine.service.TaskService;
import com.fettuccine.service.UserService;

/**
 * @author rlosard
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class, secure = false)
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	Document mockDocument = new Document("Document1", "doc1", "Bla bla bla", "today", "docx");

	String exampleDocumentJson = "{\"name\":\"doc1\",\"description\":\"Bla bla bla\",\"creationDate\":\"today\",\"type\":\"docx\"}";

	
	/**
	 * @throws Exception
	 */
	@Test
	public void retrieveDetailsForDocument() throws Exception {

		Mockito.when(userService.retrieveDocument(Mockito.anyString(), Mockito.anyString())).thenReturn(mockDocument);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/User1/documents/Document1")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());
		String expected = "{id:Document1,name:doc1,body:Bla bla bla,creationDate:today,type:docx}";

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	/**
	 * @throws Exception
	 */
	@Test
	public void createUserDocument() throws Exception {
		Document mockDocument = new Document("Document5", "doc5", "This is a new document to send", "20190305", "docx");;

		// userService.addDocument to respond back with mockDocument
		Mockito.when(userService.addDocument(Mockito.anyString(), Mockito.any(Document.class)))
		.thenReturn(mockDocument);

		// Send document as body to /users/User1/documents
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users/User1/documents")
				.accept(MediaType.APPLICATION_JSON).content(exampleDocumentJson)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.CREATED.value(), response.getStatus());

		assertEquals("http://localhost/users/User1/documents/1", response.getHeader(HttpHeaders.LOCATION));
		
		TaskService.sendTask(mockDocument.getBody());
		

	}

}
