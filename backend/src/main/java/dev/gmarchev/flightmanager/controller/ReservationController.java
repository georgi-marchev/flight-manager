package dev.gmarchev.flightmanager.controller;

import dev.gmarchev.flightmanager.dto.PageResponse;
import dev.gmarchev.flightmanager.dto.reservation.ReservationPageItem;
import dev.gmarchev.flightmanager.dto.reservation.ReservationRequest;
import dev.gmarchev.flightmanager.dto.reservation.ReservationResponse;
import dev.gmarchev.flightmanager.service.ReservationService;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

	private final ReservationService reservationService;

	@GetMapping
	public ResponseEntity<PageResponse<ReservationPageItem>> getResrevations(
			@RequestParam(required = false) @Nullable String contactEmail,
			@RequestParam int page,
			@RequestParam int size) {

		return ResponseEntity.ok(reservationService.getReservations(contactEmail, page, size));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ReservationResponse> getReservationById(@PathVariable long id) {

		return reservationService.findById(id)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<String> makeReservation(@RequestBody @Valid ReservationRequest reservationRequest) {

		reservationService.makeReservation(reservationRequest);

		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body("Reservation created successfully");
	}
}
