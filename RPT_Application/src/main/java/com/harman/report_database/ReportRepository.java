package com.harman.report_database;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
	List<Report> findAll();

	List<Report> findByEmployeeUsername(String username);
	
	Optional<Report> findById(Long id);
	
	void delete(Report report);
}
