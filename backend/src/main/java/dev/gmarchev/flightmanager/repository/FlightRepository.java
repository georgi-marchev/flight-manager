package dev.gmarchev.flightmanager.repository;

import java.time.ZonedDateTime;

import dev.gmarchev.flightmanager.model.Flight;
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

}
