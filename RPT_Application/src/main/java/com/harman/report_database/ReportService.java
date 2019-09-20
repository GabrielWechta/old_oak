package com.harman.report_database;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

	@Autowired
	private ReportRepository raportRepository;

	public List<Report> findAll() {
		return raportRepository.findAll();
	}

	public void save(Report raport) {
		raportRepository.save(raport);
	}

	public Optional<Report> findById(Long id) {
		return raportRepository.findById(id);
	}

	public List<Report> findByEmployeeUsername(String username) {
		return raportRepository.findByEmployeeUsername(username);
	}
}