package com.harman.user_database;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, String> {

	Employee findByUsername(String username);

	List<Employee> findAll();

}
