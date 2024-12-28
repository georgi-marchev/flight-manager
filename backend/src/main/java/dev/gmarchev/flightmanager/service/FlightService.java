package dev.gmarchev.flightmanager.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import dev.gmarchev.flightmanager.dto.FlightCreateRequest;
import dev.gmarchev.flightmanager.dto.FlightPageItem;
import dev.gmarchev.flightmanager.dto.FlightResponse;
import dev.gmarchev.flightmanager.dto.PageResponse;
import dev.gmarchev.flightmanager.dto.PassengerPageItem;
import dev.gmarchev.flightmanager.model.Airplane;
import dev.gmarchev.flightmanager.model.Flight;
import dev.gmarchev.flightmanager.model.Location;
import dev.gmarchev.flightmanager.model.Passenger;
import dev.gmarchev.flightmanager.model.Pilot;
import dev.gmarchev.flightmanager.model.Reservation;
import dev.gmarchev.flightmanager.repository.AirplaneRepository;
import dev.gmarchev.flightmanager.repository.FlightRepository;
import dev.gmarchev.flightmanager.repository.LocationRepository;
import dev.gmarchev.flightmanager.repository.PilotRepository;
import dev.gmarchev.flightmanager.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class FlightService {

	private final FlightRepository flightRepository;

	private final LocationRepository locationRepository;

	private final AirplaneRepository airplaneRepository;

	private final PilotRepository pilotRepository;

	private final ReservationRepository reservationRepository;

	public void createFlight(FlightCreateRequest flightCreateRequest) {

		if (flightCreateRequest.getFlightDepartureLocation() == flightCreateRequest.getFlightDestinationLocation()) {

			new IllegalArgumentException("Departure and arrival locations cannot be the same!");
		}

		if (!flightCreateRequest.getDepartureTime().isBefore(flightCreateRequest.getArrivalTime())) {

			new IllegalArgumentException("Departure time must be before arrival time!");
		}

		if (flightRepository.pilotOrPlaneAlreadyBooked(
				flightCreateRequest.getFlightPilot(),
				flightCreateRequest.getFlightAirplane(),
				flightCreateRequest.getDepartureTime(),
				flightCreateRequest.getArrivalTime())) {

			throw new IllegalArgumentException("Pilot or airplaine are not available for this time slot.");
		}

		// airplane and pilot cannot have overlapping flights
		Location departureLocation = locationRepository.findById(flightCreateRequest.getFlightDepartureLocation())
				.orElseThrow(() -> new IllegalArgumentException("Departure location cannot be found!"));

		Location destinationLocation = locationRepository.findById(flightCreateRequest.getFlightDestinationLocation())
				.orElseThrow(() -> new IllegalArgumentException("Destination location cannot be found!"));

		Airplane airplane = airplaneRepository.findById(flightCreateRequest.getFlightAirplane())
				.orElseThrow(() -> new IllegalArgumentException("Airplane cannot be found!"));

		Pilot pilot = pilotRepository.findById(flightCreateRequest.getFlightPilot())
				.orElseThrow(() -> new IllegalArgumentException("Pilot cannot be found!"));

		Flight flight = Flight.builder()
				.departureTime(flightCreateRequest.getDepartureTime())
				.arrivalTime(flightCreateRequest.getArrivalTime())
				.flightDepartureLocation(departureLocation)
				.flightDestinationLocation(destinationLocation)
				.flightAirplane(airplane)
				.flightPilot(pilot)
				.availableSeatsEconomy(airplane.getAirplaneAirplaneModel().getCapacityEconomy())
				.availableSeatsBusiness(airplane.getAirplaneAirplaneModel().getCapacityBusiness())
				.build();

		flightRepository.save(flight);

		log.info("Account created: {}", flight);
	}

	public PageResponse<FlightPageItem> getFlights(
			LocalDate departureDate,
			String departureLocation,
			String destinationLocation,
			Integer availableSeatsEconomy,
			Integer availableSeatsBusiness,
			int pageNumber,
			int pageSize) {

		Page<Flight> page = flightRepository.findFlightsByOptionalFilters(
				departureDate,
				departureLocation,
				destinationLocation,
				availableSeatsEconomy,
				availableSeatsBusiness,
				PageRequest.of(pageNumber, pageSize));

		List<FlightPageItem> flightPageItems = page.get()
				.map(f -> new FlightPageItem(
						f.getDepartureTime(),
						f.getArrivalTime(),
						locationToString(f.getFlightDepartureLocation()),
						locationToString(f.getFlightDestinationLocation())))
				.toList();

		return new PageResponse<>(flightPageItems, page.hasNext());
	}

	private static String locationToString(Location location) {

		return String.format("%s, %s (%s)", location.getCity(), location.getCountry(), location.getAirportName());
	}

	public FlightResponse getFlightById(long flightId) {

		Flight flight = flightRepository.findById(flightId)
				.orElseThrow(() -> new IllegalArgumentException("Flight not found"));

		return new FlightResponse(
				flight.getDepartureTime(),
				flight.getArrivalTime(),
				flight.getAvailableSeatsEconomy(),
				flight.getAvailableSeatsBusiness(),
				locationToString(flight.getFlightDepartureLocation()),
				locationToString(flight.getFlightDestinationLocation()));
	}

	public PageResponse<PassengerPageItem> getPassengersByFlightById(long flightId, int pageNumber, int pageSize) {

		flightRepository.findById(flightId)
				.orElseThrow(() -> new IllegalArgumentException("Flight not found"));

		Page<Reservation> page = reservationRepository.findByReservationFlightId(
				flightId, PageRequest.of(pageNumber, pageSize));

		List<PassengerPageItem> passengerPageItemList = getPassengerPageItemList(page);

		return new PageResponse<>(passengerPageItemList, page.hasNext());
	}

	private static List<PassengerPageItem> getPassengerPageItemList(Iterable<Reservation> reservations) {

		List<PassengerPageItem> passengers = new ArrayList<>();

		for (Reservation reservation : reservations) {

			for (Passenger passenger : reservation.getPassengers()) {

				passengers.add(
						new PassengerPageItem(
								reservation.getId(),
								passenger.getFirstName(),
								passenger.getMiddleName(),
								passenger.getLastName()));
			}
		}

		return passengers;
	}
}
