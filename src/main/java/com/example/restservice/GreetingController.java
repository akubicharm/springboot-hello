package com.example.restservice;

import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {
	private static Logger logger = Logger.getLogger(GreetingController.class.getName());

	private static final String template = "Hello, secretname %s!";
	private final AtomicLong counter = new AtomicLong();

	@GetMapping("/api/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		String val = getSecret(name);
		return new Greeting(counter.incrementAndGet(), String.format(template, val));
	}

	private String getSecret(String name) {
		String val = null;
		if (name != null && name.length() > 0) {
			val = System.getenv(name);
			if (val == null) val = "ENV NOT FOUND";
		}
		else {
			val = "NUL";
		}
		logger.info("##########" + name + "," + val + "##########");
		return val;
	}

}
