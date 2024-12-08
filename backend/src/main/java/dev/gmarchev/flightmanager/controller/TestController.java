package dev.gmarchev.flightmanager.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/info")
public class TestController {

	@GetMapping(value = "/ping")
	public String pong() {

		return "pong";
	}
}
