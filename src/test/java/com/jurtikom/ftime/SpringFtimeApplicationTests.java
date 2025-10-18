package com.jurtikom.ftime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import com.jurtikom.ftime.model.Greeting;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringFtimeApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void greetingListTest() {
		ResponseEntity<Greeting[]> responseEntity = restTemplate.getForEntity("/api/hello", Greeting[].class);

		Greeting[] greetings = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(greetings);
		assertEquals(3, greetings.length);
	}

	@Test
	@Order(2)
	void greetingSearchTest() {
		String urlPath = UriComponentsBuilder.fromUriString("/api/hello")
			.queryParam("message", "World!")
			.toUriString(); 

		ResponseEntity<Greeting[]> responseEntity = restTemplate.getForEntity(urlPath, Greeting[].class);

		Greeting[] greetings = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(greetings);
		assertEquals(1, greetings.length);
	}

	@Test
	@Order(3)
	void greetingGetSingleTest() {
		ResponseEntity<Greeting> responseEntity = restTemplate.getForEntity("/api/hello/2", Greeting.class);
		Greeting greeting = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(greeting);
		assertEquals(2L, greeting.getId());
	}

	@Test
	@Order(4)
	void greetingCreateTest() {
		Greeting newGreeting = new Greeting("Hello Yanzen");
		ResponseEntity<Greeting> responseEntity = restTemplate.postForEntity("/api/hello", newGreeting, Greeting.class);
		newGreeting = responseEntity.getBody();

		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertNotNull(newGreeting);
		assertEquals("Hello Yanzen", newGreeting.getMessage());
	}

	@Test
	void testUpdateGreeting() {
		Greeting greeting = new Greeting("Updated Message");
		HttpEntity<Greeting> request = new HttpEntity<>(greeting);
		ResponseEntity<Greeting> responseEntity = restTemplate.exchange("/api/hello/4", HttpMethod.PUT, request, Greeting.class);
		greeting = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(greeting);
		assertEquals("Updated Message", greeting.getMessage());
	}

	@Test
	void testDeleteGreeting() {
		ResponseEntity<Void> responseEntity = restTemplate.exchange("/api/hello/4", HttpMethod.DELETE, null, Void.class);
		assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
		assertNull(responseEntity.getBody());
	}

}
