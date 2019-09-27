package com.harman.database.services;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.harman.database.entities.Employee;
import com.harman.database.repositories.EmployeeRepository;

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

	public boolean delete(Employee employee) { // false - employee wasn't deleted, true - employee was deleted
		if (employee == null) {
			LOGGER.log(Level.SEVERE, "Employee is null. Check why do you delete null Employee.");
			return false;
		}
		if (employee.getAuthority() == "ROLE_ADMIN") {
			LOGGER.log(Level.SEVERE, "Employee is admin. No one is allowed to remove admin.");
			return false;
		}
		employeeRepository.delete(employee);
		return true;
	}

	public void save(Employee employee) {
		if (employee == null) {
			LOGGER.log(Level.SEVERE, "Employee is null. And that's bad. Check why do you save null Employee.");
			return;
		}
		employeeRepository.save(employee);
	}

	public void ensureTestData() {
		Employee employee1 = new Employee("PLIN029", "Gabi", passwordEncoder.encode("1gabi2"));
		employee1.setAuthority("Admin");
		save(employee1);
		Employee employee2 = new Employee("PW023", "Jurek", passwordEncoder.encode("ogórek"));
		employee2.setAuthority("User");
		save(employee2);
	}
}