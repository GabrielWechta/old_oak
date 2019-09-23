package com.harman.report_database;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harman.user_database.Authority;
import com.harman.user_database.AuthorityType;
import com.harman.user_database.Employee;
import com.harman.user_database.EmployeeRepository;
import com.harman.user_database.EmployeeService;

@Service
public class ReportService {

	@Autowired
	private ReportRepository reportRepository;
	private Long nextId = 1L;
	
	private static final Logger LOGGER = Logger.getLogger(EmployeeService.class.getName());

	public List<Report> findAll() {
		return reportRepository.findAll();
	}

	public void save(Report report) {
		if(report == null) {
			LOGGER.log(Level.SEVERE, "Report is null. And that's bad. Check why do you save null Report.");
			return;
		}
		Long reportId = report.getId();
		if (reportId.equals(null)) {
			report.setId(nextId);
			nextId++;
		}
		reportRepository.save(report);
	}

	public Report findById(Long id) {
		if(reportRepository.findById(id).isPresent()) {
			return reportRepository.findById(id).get();
		}
		else return new Report();
	}

	public List<Report> findByEmployeeUsername(String username) {
		return reportRepository.findByEmployeeUsername(username);
	}
	
}