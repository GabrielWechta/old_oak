package com.harman.database.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harman.database.entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, String> {

	Employee findByUsername(String username);
	Optional<Employee> findById(String id);

	List<Employee> findAll();
}
