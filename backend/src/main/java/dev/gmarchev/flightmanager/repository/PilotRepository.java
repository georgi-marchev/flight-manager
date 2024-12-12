package dev.gmarchev.flightmanager.repository;

import dev.gmarchev.flightmanager.model.Pilot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PilotRepository extends JpaRepository<Pilot, Long> {}
