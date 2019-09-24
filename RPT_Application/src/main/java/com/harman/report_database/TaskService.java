package com.harman.report_database;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harman.user_database.EmployeeService;

@Service
public class TaskService {
	@Autowired
	private TaskRepository taskRepository;

	private static final Logger LOGGER = Logger.getLogger(EmployeeService.class.getName());

	public List<Task> findAll() {
		return taskRepository.findAll();
	}

	public void save(Task task) {
		if (task == null) {
			LOGGER.log(Level.SEVERE, "Task is null. And that's bad. Check why do you save null Task.");
			return;
		}
		taskRepository.save(task);
	}
}
