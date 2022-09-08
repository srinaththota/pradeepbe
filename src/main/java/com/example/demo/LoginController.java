package com.example.demo;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

	@CrossOrigin("*")
	@PostMapping("/login")
	String login(@RequestBody User user) {
		System.out.println(user.getUsername());
		System.out.println(user.getPassword());
		return "Hello World";
	}
}
