package com.harman;

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
import com.harman.user_database.EmployeeRepository;
import com.harman.user_database.EmployeeService;

@SpringBootApplication
public class RptApplication {

	private static final Logger logger = LoggerFactory.getLogger(RptApplication.class);

	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private ReportService raportService;
	
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

//			List<Task> taski = new ArrayList<>();
//			taski.add(new Task(100L, "type", "name", "placement", "description"));
//			taskService.save(new Task("type1", "name", "placement", "description"));
//			taskService.save(new Task("type2", "name", "placement", "description"));
//			taskService.save(new Task("type3", "name", "placement", "description"));
//			taski.add(new Task(2L, "type2", "name2", "placement2", "description2"));
//			raportService.save(new Raport(1L,taskService.findAll()));
//			raportService.save(new Raport(2L,taski));
			
			employeeService.ensureTestData();
		};
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
