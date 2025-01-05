package dev.gmarchev.flightmanager.controller;

import java.util.List;

import dev.gmarchev.flightmanager.dto.AirplaneModelResponse;
import dev.gmarchev.flightmanager.service.AirplaneModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/airplane-models")
@RequiredArgsConstructor
public class AirplaneModelController {

	private final AirplaneModelService airplaneModelService;

	@GetMapping
	public ResponseEntity<List<AirplaneModelResponse>> getAirplanes() {

		return ResponseEntity.ok(airplaneModelService.getAirplaneModels());
	}
}
