package dev.gmarchev.flightmanager.repository;

import dev.gmarchev.flightmanager.model.Airplane;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirplaneRepository extends JpaRepository<Airplane, Long> {}
