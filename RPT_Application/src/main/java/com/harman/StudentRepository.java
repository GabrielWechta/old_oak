package com.harman;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long>{

	public default Long averageId() {
		Long sum = new Long(0L);
		for(Student s : findAll()) {
			sum += s.getId();
		}
		
		return sum/count();
	}
	
	public List<Student> findByName(String Name);
	
	public List<Student> findByNameStartingWith(String Name);

}
