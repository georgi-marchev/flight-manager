package dev.gmarchev.flightmanager.repository;

import dev.gmarchev.flightmanager.model.Reservation;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

	@Query("""
    SELECT r
    FROM Reservation r
    WHERE (:contactEmail IS NULL OR LOWER(r.contactEmail) LIKE LOWER(CONCAT('%', :contactEmail, '%')))
    """)
	Page<Reservation> findReservationByOptionalFilters(
			@Param("contactEmail") @Nullable String contactEmail,
			Pageable pageable);

	Page<Reservation> findByReservationFlightId(Long flightId, Pageable pageable);
}
