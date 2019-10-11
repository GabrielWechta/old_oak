package com.harman.database.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harman.database.entities.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
	List<Report> findAll();

	List<Report> findByEmployeeUsername(String username);
	
	Optional<Report> findById(Long id);
}
