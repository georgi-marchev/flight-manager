package dev.gmarchev.flightmanager.controller;

import java.time.LocalDate;

import dev.gmarchev.flightmanager.dto.FlightCreateRequest;
import dev.gmarchev.flightmanager.dto.FlightPageItem;
import dev.gmarchev.flightmanager.dto.FlightPassengerPageItem;
import dev.gmarchev.flightmanager.dto.FlightResponse;
import dev.gmarchev.flightmanager.dto.FlightUpdateRequest;
import dev.gmarchev.flightmanager.dto.PageResponse;
import dev.gmarchev.flightmanager.service.FlightService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
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
	public ResponseEntity<String> create(@RequestBody @Valid FlightCreateRequest flightCreateRequest) {

		try	{

			flightService.createFlight(flightCreateRequest);

		} catch (IllegalArgumentException e) {

			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(e.getMessage());
		}

		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body("Flight created successfully");
	}

	@PutMapping("/{flightId}")
	public ResponseEntity<String> updateFlight(
			@PathVariable @Min(value = 1, message = "Невалиден идентификатор за полет.") Long flightId,
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

		try {

			return ResponseEntity.ok(flightService.getFlightById(flightId));

		} catch (IllegalArgumentException e) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(null);
		}
	}

	@GetMapping("/{id}/passengers")
	public ResponseEntity<PageResponse<FlightPassengerPageItem>> getFlightReservationsById(
			@PathVariable long id,
			@RequestParam int page,
			@RequestParam int size) {

		// This will be in the same view as the view flight view, paginated. The data about the flight will be fetched
		// by the client from getFlightById()
		try {

			return ResponseEntity.ok(flightService.getReservationPassengersByFlightById(id, page, size));

		} catch (IllegalArgumentException e) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(null);
		}
	}
}
