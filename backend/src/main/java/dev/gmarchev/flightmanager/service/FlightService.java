package dev.gmarchev.flightmanager.service;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

import dev.gmarchev.flightmanager.dto.FlightCreateRequest;
import dev.gmarchev.flightmanager.dto.FlightPageItem;
import dev.gmarchev.flightmanager.dto.FlightPassengerPageItem;
import dev.gmarchev.flightmanager.dto.FlightResponse;
import dev.gmarchev.flightmanager.dto.FlightUpdateRequest;
import dev.gmarchev.flightmanager.dto.PageResponse;
import dev.gmarchev.flightmanager.exceptions.EntityNotFoundException;
import dev.gmarchev.flightmanager.exceptions.IvnalidFlightException;
import dev.gmarchev.flightmanager.model.Airplane;
import dev.gmarchev.flightmanager.model.Flight;
import dev.gmarchev.flightmanager.model.Location;
import dev.gmarchev.flightmanager.model.Passenger;
import dev.gmarchev.flightmanager.model.Pilot;
import dev.gmarchev.flightmanager.repository.AirplaneRepository;
import dev.gmarchev.flightmanager.repository.FlightRepository;
import dev.gmarchev.flightmanager.repository.LocationRepository;
import dev.gmarchev.flightmanager.repository.PassengerRepository;
import dev.gmarchev.flightmanager.repository.PilotRepository;
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

	private final PassengerRepository passengerRepository;

	public void createFlight(FlightCreateRequest flightCreateRequest) {

		ValidatedFlightData validatedFlightData = validateAndGetFlightData(
				flightCreateRequest.getFlightDepartureLocation(),
				flightCreateRequest.getFlightDestinationLocation(),
				flightCreateRequest.getDepartureTime(),
				flightCreateRequest.getArrivalTime(),
				flightCreateRequest.getFlightPilot(),
				flightCreateRequest.getFlightAirplane());

		Flight flight = Flight.builder()
				.departureTime(validatedFlightData.departureTime)
				.arrivalTime(validatedFlightData.arrivalTime())
				.flightDepartureLocation(validatedFlightData.departureLocation)
				.flightDestinationLocation(validatedFlightData.destinationLocation)
				.flightAirplane(validatedFlightData.airplane)
				.flightPilot(validatedFlightData.pilot)
				.availableSeatsEconomy(validatedFlightData.airplane.getAirplaneAirplaneModel().getCapacityEconomy())
				.availableSeatsBusiness(validatedFlightData.airplane.getAirplaneAirplaneModel().getCapacityBusiness())
				.build();

		flightRepository.save(flight);

		log.info("Flight created: {}", flight);
	}

	private ValidatedFlightData validateAndGetFlightData(
			Long departureLocationId,
			Long destinationLocationId,
			ZonedDateTime departureTime,
			ZonedDateTime arrivalTime,
			Long pilotId,
			Long airplaneId) {

		validateLocations(departureLocationId, destinationLocationId);
		validateTime(departureTime, arrivalTime);

		// airplane and pilot cannot have overlapping flights
		Location departureLocation = locationRepository.findById(departureLocationId)
				.orElseThrow(() -> new EntityNotFoundException(
						"Departure location cannot be found.", "Локацията за излитане не може да бъде открита."));

		Location destinationLocation = locationRepository.findById(destinationLocationId)
				.orElseThrow(() -> new EntityNotFoundException(
						"Destination location cannot be found.", "Локацията за кацане не може да бъде открита."));

		Airplane airplane = airplaneRepository.findById(airplaneId)
				.orElseThrow(() -> new EntityNotFoundException(
						"Airplane cannot be found.", "Самолет не може да бъде открит."));

		Pilot pilot = pilotRepository.findById(pilotId)
				.orElseThrow(() -> new EntityNotFoundException(
						"Pilot cannot be found.", "Пилот не може да бъде открит"));

		validatePilotAndAirplaneAvailable(pilotId, airplaneId, departureTime, arrivalTime, null);

		return new ValidatedFlightData(
				departureLocation, destinationLocation, airplane, pilot, departureTime, arrivalTime);
	}

	private void validateLocations(Long departureLocationId, Long destinationLocationId) {

		if (departureLocationId == destinationLocationId) {

			throw new IvnalidFlightException(
					"Departure and arrival locations cannot be the same.",
					"Време на излитане и кацане не могат да бъдат същите.");
		}
	}

	private void validateTime(ZonedDateTime departureTime, ZonedDateTime arrivalTime) {

		if (!departureTime.isBefore(arrivalTime)) {

			throw new IvnalidFlightException(
					"Departure time must be before arrival time.",
					"Време на излитане трябва да е преди време на кацане.");
		}
	}

	/**
	 * Validates if a pilot or a plane are booked for the requested timeslot. The logic is not designed to work
	 * perfectly - for instance it allows to book back-to-back without any gap - but it is enough to show the
	 * possible validations that could be made.
	 */
	private void validatePilotAndAirplaneAvailable(
			Long pilotId, Long airplaneId, ZonedDateTime departureTime, ZonedDateTime arrivalTime, Long flightId) {

		if (flightRepository.pilotOrPlaneAlreadyBooked(pilotId, airplaneId, departureTime, arrivalTime, flightId)) {

			throw new IvnalidFlightException(
					"Pilot or airplaine are not available for this time slot.",
					"Пилот или самолет не са налични за този времеви диапазон.");
		}
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
						f.getId(),
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
				.orElseThrow(() -> new EntityNotFoundException(
						"Flight not found.", "Полет не може да бъде открит."));

		return new FlightResponse(
				flight.getDepartureTime(),
				flight.getArrivalTime(),
				flight.getAvailableSeatsEconomy(),
				flight.getAvailableSeatsBusiness(),
				locationToString(flight.getFlightDepartureLocation()),
				locationToString(flight.getFlightDestinationLocation()),
				flight.getFlightPilot().getId());
	}

	public PageResponse<FlightPassengerPageItem> getReservationPassengersByFlightById(long flightId, int pageNumber, int pageSize) {

		flightRepository.findById(flightId)
				.orElseThrow(() -> new EntityNotFoundException(
						"Flight not found.", "Полет не може да бъде открит."));

		Page<Passenger> page = passengerRepository
				.findAllPassengersByFlightId(flightId, PageRequest.of(pageNumber, pageSize));

		return new PageResponse<>(getReservationPageItemList(page), page.hasNext());
	}

	private static List<FlightPassengerPageItem> getReservationPageItemList(Page<Passenger> passengers) {

		return passengers
				.stream()
				.map(p -> new FlightPassengerPageItem(
						p.getId(),
						p.getPassengerReservation().getId(),
						p.getFirstName(),
						p.getMiddleName(),
						p.getLastName(),
						p.getPersonalIdentificationNumber(),
						p.getPhoneNumber(),
						p.getNationality(),
						p.getSeatType()
				))
				.toList();
	}

	public void updateFlight(Long flightId, FlightUpdateRequest flightUpdateRequest) {

		Flight flight = flightRepository.findById(flightId)
				.orElseThrow(() -> new EntityNotFoundException(
						"Flight cannot be found.", "Полетът не може да бъде открит."));

		validateTime(flightUpdateRequest.getDepartureTime(), flightUpdateRequest.getArrivalTime());

		Pilot pilot = pilotRepository.findById(flightUpdateRequest.getPilotId())
				.orElseThrow(() -> new EntityNotFoundException(
						"Pilot cannot be found.", "Пилот не може да бъде открит"));

		validatePilotAndAirplaneAvailable(
				flightUpdateRequest.getPilotId(),
				flight.getFlightAirplane().getId(),
				flightUpdateRequest.getDepartureTime(),
				flightUpdateRequest.getArrivalTime(),
				flightId);

		flight.setDepartureTime(flightUpdateRequest.getDepartureTime());
		flight.setArrivalTime(flightUpdateRequest.getArrivalTime());
		flight.setFlightPilot(pilot);

		flightRepository.save(flight);

		log.info("Flight updated: {}", flight);
	}

	private record ValidatedFlightData(
			Location departureLocation,
			Location destinationLocation,
			Airplane airplane,
			Pilot pilot,
			ZonedDateTime departureTime,
			ZonedDateTime arrivalTime) {}
}
