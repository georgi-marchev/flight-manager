package dev.gmarchev.flightmanager.repository;

import java.util.Optional;

import dev.gmarchev.flightmanager.model.Role;
import dev.gmarchev.flightmanager.model.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByName(RoleType name);
}
