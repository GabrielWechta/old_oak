package com.harman;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.harman.database.Employee;
import com.harman.database.EmployeeRepository;
import com.harman.database.UserDetailsServiceImpl;
import com.harman.database.UserDao;

@SpringBootApplication
public class RptApplication implements CommandLineRunner{

	private static final Logger logger = LoggerFactory.getLogger(RptApplication.class);

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private EmployeeRepository employeeRepository;

	
    public static void main(String[] args) {
        SpringApplication.run(RptApplication.class, args);
        logger.info("----------logger in RptApllication ended----------------");
    }
    
    @Override
    public void run(String... args) throws Exception{ 
    	
    	System.out.println("++++++++++++++++++++++++++++++++++++++++HIBERNATE++++++++++++++++++++++++++++++++++++++++++++");
    	//logger.info("Student id 1001 -> {}", repository.findById(1001L));
    	
    	logger.info("All users 1 -> {}", employeeRepository.findByusername("Geralt"));
    	
    	//Insert
    	logger.info("Inserting -> {}", employeeRepository.save(new Employee("Jaskier",  passwordEncoder.encode("cudny"))));
    	logger.info("Inserting -> {}", employeeRepository.save(new Employee("Triss",  passwordEncoder.encode("kasztan"))));
    }
    
	@Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
     return new BCryptPasswordEncoder();
    }
	
}
