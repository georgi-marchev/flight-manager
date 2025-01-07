package dev.gmarchev.flightmanager.controller;

import java.time.LocalDate;

import dev.gmarchev.flightmanager.dto.flight.FlightCreateRequest;
import dev.gmarchev.flightmanager.dto.flight.FlightPageItem;
import dev.gmarchev.flightmanager.dto.flight.FlightPassengerPageItem;
import dev.gmarchev.flightmanager.dto.flight.FlightResponse;
import dev.gmarchev.flightmanager.dto.flight.FlightUpdateRequest;
import dev.gmarchev.flightmanager.dto.PageResponse;
import dev.gmarchev.flightmanager.service.FlightService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/flights")
@RequiredArgsConstructor
public class FlightController {

	private final FlightService flightService;

	@PostMapping
	public ResponseEntity<String> createFlight(@RequestBody @Valid FlightCreateRequest flightCreateRequest) {

		flightService.createFlight(flightCreateRequest);

		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body("Flight created successfully");
	}

	@PutMapping("/{flightId}")
	public ResponseEntity<String> updateFlight(
			@PathVariable long flightId,
			@RequestBody @Valid FlightUpdateRequest flightUpdateRequest) {

		flightService.updateFlight(flightId, flightUpdateRequest);

		return ResponseEntity
				.status(HttpStatus.NO_CONTENT)
				.body("Flight updated successfully ");
	}

	@GetMapping
	public ResponseEntity<PageResponse<FlightPageItem>> getFlights(
			@RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate departureDate,
			@RequestParam(required = false) String departureLocation,
			@RequestParam(required = false) String destinationLocation,
			@RequestParam(required = false) Integer availableSeatsEconomy,
			@RequestParam(required = false) Integer availableSeatsBusiness,
			@RequestParam int page,
			@RequestParam int size) {

		return ResponseEntity.ok(flightService.getFlights(
				departureDate,
				departureLocation,
				destinationLocation,
				availableSeatsEconomy,
				availableSeatsBusiness,
				page,
				size));
	}

	@GetMapping("/{flightId}")
	public ResponseEntity<FlightResponse> getFlightById(@PathVariable long flightId) {

		return ResponseEntity.ok(flightService.getFlightById(flightId));
	}

	@GetMapping("/{id}/passengers")
	public ResponseEntity<PageResponse<FlightPassengerPageItem>> getFlightReservationsById(
			@PathVariable long id,
			@RequestParam int page,
			@RequestParam int size) {

		return ResponseEntity.ok(flightService.getReservationPassengersByFlightById(id, page, size));
	}
}
