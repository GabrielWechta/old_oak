package com.harman;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.harman.user_database.EmployeeRepository;
import com.harman.user_database.EmployeeService;

@SpringBootApplication
public class RptApplication {

	private static final Logger logger = LoggerFactory.getLogger(RptApplication.class);

	@Autowired
	private EmployeeService employeeService;

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
