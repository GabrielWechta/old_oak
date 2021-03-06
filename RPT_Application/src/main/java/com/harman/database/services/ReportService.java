package com.harman.database.services;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harman.database.entities.Report;
import com.harman.database.repositories.ReportRepository;

@Service
public class ReportService {

	@Autowired
	private ReportRepository reportRepository;

	private static final Logger LOGGER = Logger.getLogger(EmployeeService.class.getName());

	public List<Report> findAll() {
		return reportRepository.findAll();
	}

	public void save(Report report) {
		if (report == null) {
			LOGGER.log(Level.SEVERE, "Report is null. And that's bad. Check why do you save null Report.");
			return;
		}
		reportRepository.save(report);
	}

	public Report findById(Long id) {
		if (reportRepository.findById(id).isPresent()) {
			return reportRepository.findById(id).get();
		} else
			return null;
	}

	public List<Report> findByEmployeeUsername(String username) {
		return reportRepository.findByEmployeeUsername(username);
	}

	public void delete(Report report) {
		if (report == null) {
			LOGGER.log(Level.SEVERE, "Report is null. Check why do you delete null report.");
			return;
		}
		LOGGER.log(Level.SEVERE, "WSZEDLEM");
		reportRepository.delete(report);
		LOGGER.log(Level.SEVERE, "DZIALAM");
	}

}