package com.harman.database.services;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harman.database.entities.Report;
import com.harman.database.entities.Task;
import com.harman.database.repositories.TaskRepository;

@Service
public class TaskService {
	@Autowired
	private TaskRepository taskRepository;

	private static final Logger LOGGER = Logger.getLogger(EmployeeService.class.getName());

	public List<Task> findAll() {
		return taskRepository.findAll();
	}
	
	public List<Task> findByReport(Report report) {
		return taskRepository.findByReport(report);
	}
	
	public void save(Task task) {
		if (task == null) {
			LOGGER.log(Level.SEVERE, "Task is null. And that's bad. Check why do you save null Task.");
			return;
		}
		taskRepository.save(task);
	}
	
	
}
