package com.harman.user_database;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, String> {

	Employee findByUsername(String username);
	Optional<Employee> findById(String id);

	List<Employee> findAll();
	


}
