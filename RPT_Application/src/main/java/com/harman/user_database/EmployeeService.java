package com.harman.user_database;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

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

	public void delete(Employee employee) {
		if (employee == null) {
			LOGGER.log(Level.SEVERE, "Employee is null. Check why do you delete null Employee.");
			return;
		}
		employeeRepository.delete(employee);
	}

	public void save(Employee employee) {
		if (employee == null) {
			LOGGER.log(Level.SEVERE, "Employee is null. And that's bad. Check why do you save null Employee.");
			return;
		}
		employeeRepository.save(employee);
	}

	public void ensureTestData() {
		save(new Employee("PLIN029", "Gabi", passwordEncoder.encode("1gabi2"),
				(new Authority(AuthorityType.ROLE_ADMIN))));
		save(new Employee("PW023", "Jurek", passwordEncoder.encode("ogórek"),
				(new Authority(AuthorityType.ROLE_USER))));
	}
}