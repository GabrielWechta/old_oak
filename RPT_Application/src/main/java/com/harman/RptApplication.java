package com.harman;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.harman.database.Employee;
import com.harman.database.EmployeeRepository;

@SpringBootApplication
public class RptApplication implements CommandLineRunner{

	private static final Logger logger = LoggerFactory.getLogger(RptApplication.class);
	
	@Autowired
	EmployeeRepository repository;
	
    public static void main(String[] args) {
    	logger.info("++++++++++++logger in RptApllication started++++++++++++++++++++");
        SpringApplication.run(RptApplication.class, args);
        logger.info("----------logger in RptApllication ended----------------");
    }
    
    @Override
    public void run(String... args) throws Exception{ 
    	
    	System.out.println("++++++++++++++++++++++++++++++++++++++++HIBERNATE++++++++++++++++++++++++++++++++++++++++++++");
    	//logger.info("Student id 1001 -> {}", repository.findById(1001L));
    	
    	//logger.info("All users 1 -> {}", repository.findAll());
    	
//    	//Insert
//    	logger.info("Inserting -> {}", repository.save(new Student(1005L, "Jaskier", "J1234657")));
//    	logger.info("Inserting -> {}", repository.save(new Student(1006L, "Vesemir", "V1234657")));
//    	logger.info("Inserting -> {}", repository.save(new Student(1007L, "Eskel", "E1234657")));
//
//    	logger.info("where is Geralt -> {}", repository.findByName("Geralt"));
//    	logger.info("where is Ciri -> {}", repository.findByName("Ciri"));
//    	
//    	logger.info("where are Ciris -> {}", repository.findByNameStartingWith("Ci"));
//
//    	
//    	//logger.info("All users 2 -> {}", repository.findAll());s
//    	
//    	logger.info("My function -> {}", repository.averageId());
    }
}
