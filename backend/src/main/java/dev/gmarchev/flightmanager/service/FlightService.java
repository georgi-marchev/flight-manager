package dev.gmarchev.flightmanager.service;

import dev.gmarchev.flightmanager.dto.FlightRequest;
import dev.gmarchev.flightmanager.model.Airplane;
import dev.gmarchev.flightmanager.model.Flight;
import dev.gmarchev.flightmanager.model.Location;
import dev.gmarchev.flightmanager.model.Pilot;
import dev.gmarchev.flightmanager.repository.AirplaneModelRepository;
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

	public void createFlight(FlightRequest flightRequest) {

		// TODO: Validation
		// airplane and pilot cannot have overlapping flights
		Location departureLocation = locationRepository.findById(flightRequest.getFlightDepartureLocation())
				.orElseThrow(() -> new IllegalArgumentException("Departure location cannot be found!"));

		Location destinationLocation = locationRepository.findById(flightRequest.getFlightDestinationLocation())
				.orElseThrow(() -> new IllegalArgumentException("Destination location cannot be found!"));

		Airplane airplane = airplaneRepository.findById(flightRequest.getFlightAirplane())
				.orElseThrow(() -> new IllegalArgumentException("Airplane cannot be found!"));

		Pilot pilot = pilotRepository.findById(flightRequest.getFlightPilot())
				.orElseThrow(() -> new IllegalArgumentException("Pilot cannot be found!"));

		Flight flight = Flight.builder()
				.departureTime(flightRequest.getDepartureTime())
				.arrivalTime(flightRequest.getArrivalTime())
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
