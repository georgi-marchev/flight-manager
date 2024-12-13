package dev.gmarchev.flightmanager.repository;

import dev.gmarchev.flightmanager.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ReservationRepository extends
		JpaRepository<Reservation, Long>,
		JpaSpecificationExecutor<Reservation> {}
