package dev.gmarchev.flightmanager.repository;

import dev.gmarchev.flightmanager.model.Passenger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {

	@Query("""
       SELECT p
       FROM Passenger p
       JOIN p.passengerReservation r
       WHERE r.reservationFlight.id = :flightId
       """)
	Page<Passenger> findAllPassengersByFlightId(Long flightId, Pageable pageable);

}
