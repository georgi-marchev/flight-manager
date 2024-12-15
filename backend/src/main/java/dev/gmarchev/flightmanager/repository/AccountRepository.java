package dev.gmarchev.flightmanager.repository;

import java.util.Optional;

import dev.gmarchev.flightmanager.model.Account;

import dev.gmarchev.flightmanager.model.RoleType;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<Account, Long> {

	Optional<Account> findFirstByRolesName(RoleType roleType);

	Optional<Account> findByUsername(String username);

	@Query("""
    SELECT a
    FROM Account a
    JOIN a.roles r
    WHERE r.name = :role
    AND (:username IS NULL OR LOWER(a.username) LIKE LOWER(CONCAT('%', :username, '%')))
    AND (:email IS NULL OR LOWER(a.email) LIKE LOWER(CONCAT('%', :email, '%')))
    AND (:firstName IS NULL OR LOWER(a.firstName) LIKE LOWER(CONCAT('%', :firstName, '%')))
    AND (:lastName IS NULL OR LOWER(a.lastName) LIKE LOWER(CONCAT('%', :lastName, '%')))
    """)
	Page<Account> findReservationByOptionalFilters(
			@Param("username") @Nullable String username,
			@Param("email") @Nullable String email,
			@Param("firstName") @Nullable String firstName,
			@Param("lastName") @Nullable String lastName,
			@Param("role") RoleType role,
			Pageable pageable);
}
