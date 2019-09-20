package com.harman.report_database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
	@Autowired
	private TaskRepository taskRepository;

	public List<Task> findAll() {
		return taskRepository.findAll();
	}

	public void save(Task task) {
		taskRepository.save(task);

	}
}
