package com.harman.database;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{

	public default Long averageId() {
		Long sum = new Long(0L);
		for(Employee s : findAll()) {
			sum += s.getId();
		} 
		
		return sum/count();
	}
	
	//public List<Employee> findByName(String Name);
	
	//public List<Employee> findByNameStartingWith(String Name);

}
