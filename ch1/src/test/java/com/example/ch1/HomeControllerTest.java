package com.example.ch1;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

//Your test will perform an HTTP GET request for the
//root path / and expect a successful result where the view name is home and the resulting
//content contains the phrase “Welcome to...”

@RunWith(SpringRunner.class)
@WebMvcTest(HomeController.class)  // <--1

public class HomeControllerTest {

	@Autowired // <--2
	private MockMvc mockMvc;
	
	// The testHomePage() method defines the test you want to perform against the homepage.
	@Test
	public void testHomePage() throws Exception {
		
		mockMvc.perform(get("/")) // <--3
			.andExpect(status().isOk()) // <--4
			.andExpect(view().name("home")) // <--5
			.andExpect(content().string(containsString("Welcome to..."))); //<--6
	}
}

// 1. Web test for home controller -> special test annotation that arranges for the test to run in
// the context of a Spring MVC application.
// 2. Injects MockMvc -> test class is injected with a MockMvc object for the test to drive the mockup.
// 3. Performs GET request to '/'
// 4. Expects and HTTP 200 message
// 5. Expects the home view
// 6. Expects 'Welcome to...' to show on screen