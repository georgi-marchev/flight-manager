package dev.gmarchev.flightmanager.service;

import dev.gmarchev.flightmanager.dto.FlightCreateRequest;
import dev.gmarchev.flightmanager.model.Airplane;
import dev.gmarchev.flightmanager.model.Flight;
import dev.gmarchev.flightmanager.model.Location;
import dev.gmarchev.flightmanager.model.Pilot;
import dev.gmarchev.flightmanager.repository.AirplaneRepository;
import dev.gmarchev.flightmanager.repository.FlightRepository;
import dev.gmarchev.flightmanager.repository.LocationRepository;
import dev.gmarchev.flightmanager.repository.PilotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class FlightService {

	private final FlightRepository flightRepository;

	private final LocationRepository locationRepository;

	private final AirplaneRepository airplaneRepository;

	private final PilotRepository pilotRepository;

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
}
