package dev.gmarchev.flightmanager.repository;

import dev.gmarchev.flightmanager.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {}
