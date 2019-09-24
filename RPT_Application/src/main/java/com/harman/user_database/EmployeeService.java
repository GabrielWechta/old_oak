package com.harman.user_database;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.harman.report_database.Report;
import com.harman.report_database.ReportService;
import com.harman.report_database.Task;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private ReportService raportService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	private static final Logger LOGGER = Logger.getLogger(EmployeeService.class.getName());

	private EmployeeService() {
	}

	public List<Employee> findAll() {
		return employeeRepository.findAll();
	}

	public int count() {
		return (int) employeeRepository.count();
	}

	public Employee findByUsername(String username) {
		return employeeRepository.findByUsername(username);
	}

	public Employee findById(String id) {
			return employeeRepository.findById(id).orElse(null);
	}

	public void delete(Employee entry) {
		if (entry == null) {
			LOGGER.log(Level.SEVERE, "Employee is null. Check why do you delete null Employee.");
			return;
		}
		employeeRepository.delete(entry);
	}

	public void save(Employee entry) {
		if (entry == null) {
			LOGGER.log(Level.SEVERE, "Employee is null. And that's bad. Check why do you save null Employee.");
			return;
		}
		employeeRepository.save(entry);
	}

	public void ensureTestData() {
		save(new Employee("PLIN029", "Gabi", passwordEncoder.encode("1gabi2"),
				(new Authority(AuthorityType.ROLE_ADMIN))));
		save(new Employee("PW023", "Jurek", passwordEncoder.encode("ogórek"),
				(new Authority(AuthorityType.ROLE_USER))));
	}
}