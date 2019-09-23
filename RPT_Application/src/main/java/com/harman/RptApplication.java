package com.harman;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.harman.report_database.Report;
import com.harman.report_database.ReportService;
import com.harman.report_database.Task;
import com.harman.report_database.TaskService;
import com.harman.user_database.Employee;
import com.harman.user_database.EmployeeRepository;
import com.harman.user_database.EmployeeService;

@SpringBootApplication
public class RptApplication {

	private static final Logger logger = LoggerFactory.getLogger(RptApplication.class);

	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private ReportService reportService;
	
	@Autowired
	private TaskService taskService;

	public static void main(String[] args) {
		SpringApplication.run(RptApplication.class, args);
		logger.info("----------logger in RptApllication ended----------------");
	}

	@Bean
	public CommandLineRunner loadData(EmployeeRepository employeeRepository) {
		return (args) -> {
			System.out.println(
					"++++++++++++++++++++++++++++++++++++++++HIBERNATE++++++++++++++++++++++++++++++++++++++++++++");

			employeeService.ensureTestData();
		};
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
