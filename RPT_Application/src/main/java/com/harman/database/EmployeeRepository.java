package com.harman.database;


import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, String>{
	
	Employee findByusername(String username);
	List<Employee> findAll();

}
