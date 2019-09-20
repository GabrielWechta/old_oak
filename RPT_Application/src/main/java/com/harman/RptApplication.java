package com.harman;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.harman.raport_database.Raport;
import com.harman.raport_database.RaportService;
import com.harman.raport_database.Task;
import com.harman.raport_database.TaskService;
import com.harman.user_database.EmployeeRepository;
import com.harman.user_database.EmployeeService;

@SpringBootApplication
public class RptApplication {

	private static final Logger logger = LoggerFactory.getLogger(RptApplication.class);

	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private RaportService raportService;
	
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
			
			System.out.println("dupa" + employeeService.findById(Integer.toString(31)).get().getUsername());
			
//			List<Task> taski = new ArrayList<>();
//			taski.add(new Task(100L, "type", "name", "placement", "description"));
			taskService.save(new Task("type1", "name", "placement", "description"));
			taskService.save(new Task("type2", "name", "placement", "description"));
			taskService.save(new Task("type3", "name", "placement", "description"));
	//		taski.add(new Task(2L, "type2", "name2", "placement2", "description2"));
			raportService.save(new Raport(1L,taskService.findAll()));
			//raportService.save(new Raport(2L,taski));
		};
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
