package dev.gmarchev.flightmanager.controller;

import dev.gmarchev.flightmanager.model.Test;
import dev.gmarchev.flightmanager.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/info")
public class TestController {

	private final TestRepository testRepository;

	@Autowired
	public TestController(TestRepository testRepository) {

		this.testRepository = testRepository;
	}

	@GetMapping(value = "/ping")
	public String pong() {

		return "pong";
	}

	@PostMapping(value = "/create-test")
	public ResponseEntity<Long> createTest() {

		Test test = new Test();

		testRepository.save(test);

		return ResponseEntity.ok(test.id);
	}
}
