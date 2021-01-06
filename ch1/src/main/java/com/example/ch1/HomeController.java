package com.example.ch1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// Here is a simple controller class that handles requests for the root path
// and forwards them to the homepage view without populating model data.

@Controller     // <--1
public class HomeController {

	@GetMapping("/")   // <--2
	public String home() {
		return "home"; // <--3
	}
}

// 1. The controller -> primary purpose is to identify this class as a component for component scanning
// 2. Handles requests for root path ('/') -> that if an HTTP GET request is received for the root,
// then this method should handle that request
// 3. Returns the view name -> is the logical name of view