package dev.gmarchev.flightmanager.repository;

import dev.gmarchev.flightmanager.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flight, Long> {}
