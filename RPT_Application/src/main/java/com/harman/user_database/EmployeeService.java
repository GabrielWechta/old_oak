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

	public Employee findById(String id) {
		if (employeeRepository.findById(id).isPresent()) {
			return employeeRepository.findById(id).get();
		} else {
			return new Employee("Error employee, your account has been deleted while using app", "mistakeshavebeenamde",
					new Authority(AuthorityType.ROLE_USER));
		}
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

		Long index = 1L;
		for (String name : names) {
			String[] split = name.split(" ");
//
//			Task task = new Task("type1", "name " + index, "placement", "description");
//			List<Task> taskList = new ArrayList<>();
//			taskList.add(task);
//			index++;
//			Task task2 = new Task("type1", "name " + index, "placement", "description");
//			Report raport = new Report(taskList);
//			List<Task> taskList2 = new ArrayList<>();
//			taskList2.add(task2);
//			Report raport2 = new Report(taskList2);
//
//			List<Report> raportList = new ArrayList<>();
//			raportList.add(raport);
//			raportList.add(raport2);

			index++;
			Employee c = new Employee(split[0], passwordEncoder.encode(split[1]),
					(new Authority(AuthorityType.ROLE_USER)));
			save(c);
//			raport.setEmployee(c);
//			raport2.setEmployee(c);
//			raportService.save(raport);
//			raportService.save(raport2);
		}
		save(new Employee("Gabi", passwordEncoder.encode("1gabi2"), (new Authority(AuthorityType.ROLE_ADMIN))));
	}

}