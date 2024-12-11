package dev.gmarchev.flightmanager.repository;

import java.util.Optional;
import java.util.Set;

import dev.gmarchev.flightmanager.model.Account;
import dev.gmarchev.flightmanager.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

	Set<Account> findByRoles(Role role);

	Optional<Account> findByUsername(String username);
}
