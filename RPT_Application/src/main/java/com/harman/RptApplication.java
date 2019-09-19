package com.harman;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.harman.raport_database.Raport;
import com.harman.raport_database.RaportService;
import com.harman.raport_database.Task;
import com.harman.user_database.EmployeeRepository;
import com.harman.user_database.EmployeeService;

@SpringBootApplication
@ComponentScan("")
public class RptApplication {

	private static final Logger logger = LoggerFactory.getLogger(RptApplication.class);

	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private RaportService raportService;

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
			
			Set<Task> taski = new HashSet<>();
			taski.add(new Task(1L, "type", "name", "placement", "description"));
			//taski.add(new Task(2L, "type2", "name2", "placement2", "description2"));
			raportService.save(new Raport(1L,taski));
			raportService.save(new Raport(2L,taski));
		};
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
