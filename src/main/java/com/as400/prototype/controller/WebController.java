package com.as400.prototype.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

	private static final Logger logger = LoggerFactory.getLogger(WebController.class);
	
	@GetMapping(value = "/test")
	public String testApi(@RequestBody String body) {
		logger.info("Starting testAPi()...");
		return "test success";	
	}
}
