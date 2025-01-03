package dev.gmarchev.flightmanager.repository;

import java.time.LocalDate;
import java.time.ZonedDateTime;

import dev.gmarchev.flightmanager.model.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FlightRepository extends JpaRepository<Flight, Long> {

	// Check if the flight duration overlaps with any other flights for the same pilot or plane
	@Query("""
    SELECT COUNT(flight) > 0
    FROM Flight flight
    WHERE (flight.flightPilot.id = :pilotId OR flight.flightAirplane.id = :airplaneId)
    AND (
    	(
    		flight.departureTime BETWEEN :startTime AND :endTime)
    		OR (flight.arrivalTime BETWEEN :startTime AND :endTime
    	)
    	OR (:startTime BETWEEN flight.departureTime AND flight.arrivalTime)
    	OR (:endTime BETWEEN flight.departureTime AND flight.arrivalTime)
    )
    """)
	boolean pilotOrPlaneAlreadyBooked(
			@Param("pilotId") Long pilotId,
			@Param("airplaneId") Long airplaneId,
			@Param("startTime") ZonedDateTime startTime,
			@Param("endTime") ZonedDateTime endTime);

	@Query("""
    SELECT f
    FROM Flight f
    WHERE (:departureDate IS NULL OR CAST(f.departureTime AS DATE) = :departureDate)
    AND (
    	:departureLocation IS NULL
    	OR LOWER(f.flightDepartureLocation.airportName) LIKE LOWER(CONCAT('%', :departureLocation, '%'))
    	OR LOWER(f.flightDepartureLocation.city) LIKE LOWER(CONCAT('%', :departureLocation, '%'))
    	OR LOWER(f.flightDepartureLocation.country) LIKE LOWER(CONCAT('%', :departureLocation, '%')))
    AND (
    	:destinationLocation IS NULL
    	OR LOWER(f.flightDestinationLocation.airportName) LIKE LOWER(CONCAT('%', :destinationLocation, '%'))
    	OR LOWER(f.flightDestinationLocation.city) LIKE LOWER(CONCAT('%', :destinationLocation, '%'))
    	OR LOWER(f.flightDestinationLocation.country) LIKE LOWER(CONCAT('%', :destinationLocation, '%')))
    AND (:availableSeatsEconomy IS NULL OR f.availableSeatsEconomy >= :availableSeatsEconomy)
    AND (:availableSeatsBusiness IS NULL OR f.availableSeatsBusiness >= :availableSeatsBusiness)
    """)
	Page<Flight> findFlightsByOptionalFilters(
			@Param("departureDate") LocalDate departureDate,
			@Param("departureLocation") String departureLocation,
			@Param("destinationLocation") String destinationLocation,
			@Param("availableSeatsEconomy") Integer availableSeatsEconomy,
			@Param("availableSeatsBusiness") Integer availableSeatsBusiness,
			Pageable pageable);

}
