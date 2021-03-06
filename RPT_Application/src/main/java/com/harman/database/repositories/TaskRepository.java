package com.harman.database.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harman.database.entities.Report;
import com.harman.database.entities.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

	List<Task> findByReport(Report report);

}