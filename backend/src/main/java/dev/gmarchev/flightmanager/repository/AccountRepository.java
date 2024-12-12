package dev.gmarchev.flightmanager.repository;

import java.util.Optional;

import dev.gmarchev.flightmanager.model.Account;

import dev.gmarchev.flightmanager.model.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AccountRepository extends JpaRepository<Account, Long>, JpaSpecificationExecutor<Account> {

	Optional<Account> findFirstByRolesName(RoleType roleType);

	Optional<Account> findByUsername(String username);
}
