package dev.gmarchev.flightmanager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import dev.gmarchev.flightmanager.dto.PageResponse;
import dev.gmarchev.flightmanager.dto.reservation.ReservationRequestPassenger;
import dev.gmarchev.flightmanager.dto.reservation.ReservationPageItem;
import dev.gmarchev.flightmanager.dto.reservation.ReservationPassengerResponse;
import dev.gmarchev.flightmanager.dto.reservation.ReservationRequest;
import dev.gmarchev.flightmanager.dto.reservation.ReservationResponse;
import dev.gmarchev.flightmanager.exceptions.EntityNotFoundException;
import dev.gmarchev.flightmanager.exceptions.InsufficientSeatsException;
import dev.gmarchev.flightmanager.model.Flight;
import dev.gmarchev.flightmanager.model.Passenger;
import dev.gmarchev.flightmanager.model.Reservation;
import dev.gmarchev.flightmanager.model.SeatType;
import dev.gmarchev.flightmanager.repository.FlightRepository;
import dev.gmarchev.flightmanager.repository.ReservationRepository;
import jakarta.annotation.Nullable;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {

	private final FlightRepository flightRepository;

	private final ReservationRepository reservationRepository;

	private final ReservationNotificationService reservationNotificationService;

	public PageResponse<ReservationPageItem> getReservations(
			@Nullable String contactEmail, int pageNumber, int pageSize) {

		Page<Reservation> page = reservationRepository.findReservationByOptionalFilters(
				contactEmail, PageRequest.of(pageNumber, pageSize));

		List<ReservationPageItem> reservations = page.get()
				.map(r -> new ReservationPageItem(
						r.getId(), r.getReservationFlight().getId(), r.getContactEmail()))
				.toList();

		return new PageResponse<>(reservations, page.hasNext());
	}

	@Retryable(
			retryFor = OptimisticLockingFailureException.class,
			maxAttempts = 3,
			backoff = @Backoff(delay = 1000))
	@Transactional
	public void makeReservation(ReservationRequest reservationRequest) throws InsufficientSeatsException {

		// Flight entity is versioned, so there will be optimistic lock on Flight during the transaction. If
		// OptimisticLockingFailureException is thrown, the method will retry to make the reservation until exhausting
		// the retry maxAttempts.
		Flight flight = flightRepository.findById(reservationRequest.getFlightId())
				.orElseThrow(() -> new EntityNotFoundException(
						String.format(
								"Flight with ID %d was not found in the database", reservationRequest.getFlightId()),
						"Полет не може да бъде намерен."));

		int economySeatsToBook = 0;
		int businessSeatsToBook = 0;

		List<Passenger> passengers = new ArrayList<>();

		for (ReservationRequestPassenger reservationRequestPassenger : reservationRequest.getPassengers()) {

			SeatType seatType = reservationRequestPassenger.getSeatType();

			if (seatType.equals(SeatType.ECONOMY)) {

				economySeatsToBook++;

			} else if (seatType.equals(SeatType.BUSINESS)) {

				businessSeatsToBook++;

			} else {

				throw new UnsupportedOperationException(String.format("Unsupported SeatType '%s'", seatType));
			}

			passengers.add(
					Passenger.builder()
							.firstName(reservationRequestPassenger.getFirstName())
							.middleName(reservationRequestPassenger.getMiddleName())
							.lastName(reservationRequestPassenger.getLastName())
							.personalIdentificationNumber(reservationRequestPassenger.getPersonalIdentificationNumber())
							.phoneNumber(reservationRequestPassenger.getPhoneNumber())
							.nationality(reservationRequestPassenger.getNationality())
							.seatType(reservationRequestPassenger.getSeatType())
							.build());
		}

		int availableSeatsEconomy = flight.getAvailableSeatsEconomy();
		int availableSeatsBusiness = flight.getAvailableSeatsBusiness();

		if (availableSeatsEconomy < economySeatsToBook || availableSeatsBusiness < businessSeatsToBook) {

			throw new InsufficientSeatsException();
		}

		flight.setAvailableSeatsEconomy(availableSeatsEconomy - economySeatsToBook);
		flight.setAvailableSeatsBusiness(availableSeatsBusiness - businessSeatsToBook);

		Reservation reservation = Reservation.builder()
				.reservationFlight(flight)
				.contactEmail(reservationRequest.getContactEmail())
				.passengers(passengers)
				.build();


		reservationRepository.save(reservation);
		flightRepository.save(flight);

		notifyCustomer(reservation);
	}

	private void notifyCustomer(Reservation reservation) {

		reservationNotificationService.sendCompletedReservationMessage(reservation);
	}

	public Optional<ReservationResponse> findById(Long id) {

		ReservationResponse reservationResponse = reservationRepository.findById(id)
				.map(r -> new ReservationResponse(
						r.getContactEmail(),
						r.getReservationFlight().getId(),
						r.getPassengers().stream()
								.map(p -> ReservationPassengerResponse.builder()
										.firstName(p.getFirstName())
										.middleName(p.getMiddleName())
										.lastName(p.getLastName())
										.nationality(p.getNationality())
										.personalIdentificationNumber(p.getPersonalIdentificationNumber())
										.phoneNumber(p.getPhoneNumber())
										.seatType(p.getSeatType())
										.build())
								.toList()))
				.orElse(null);

		return Optional.ofNullable(reservationResponse);
	}
}
