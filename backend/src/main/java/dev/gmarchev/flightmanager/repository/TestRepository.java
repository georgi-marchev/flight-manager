package dev.gmarchev.flightmanager.repository;

import dev.gmarchev.flightmanager.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {}
