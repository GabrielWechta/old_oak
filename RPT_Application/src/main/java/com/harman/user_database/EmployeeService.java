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
	private int nextId = 1;

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
		if (entry.getId() == null) {
			entry.setId(Integer.toString(nextId));
			nextId++;
		}

		employeeRepository.save(entry);
	}

	public void ensureTestData() {
		final String[] names = new String[] { "Gabrielle Patel", "Brian Robinson", "Eduardo Haugen", "Koen Johansen",
				"Alejandro Macdonald", "Angel Karlsson", "Yahir Gustavsson", "Haiden Svensson", "Emily Stewart",
				"Corinne Davis", "Ryann Davis", "Yurem Jackson", "Kelly Gustavsson", "Eileen Walker", "Katelyn Martin",
				"Israel Carlsson", "Quinn Hansson", "Makena Smith", "Danielle Watson", "Leland Harris",
				"Gunner Karlsen", "Jamar Olsson", "Lara Martin", "Ann Andersson", "Remington Andersson",
				"Rene Carlsson", "Elvis Olsen", "Solomon Olsen", "Jaydan Jackson", "Bernard Nilsen" };
		for (String name : names) {
			String[] split = name.split(" ");
			Employee c = new Employee(split[0], passwordEncoder.encode(split[1]), (new Authority(AuthorityType.ROLE_USER)));
			save(c);
		}
		save(new Employee("Gabi", passwordEncoder.encode("1gabi2"), (new Authority(AuthorityType.ROLE_ADMIN))));
	}
}