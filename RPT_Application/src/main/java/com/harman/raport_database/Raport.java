package com.harman.raport_database;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.harman.user_database.Employee;

@Entity
@Table(name = "raport")
public class Raport {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Task> tasks = new ArrayList<Task>();

	@ManyToOne(targetEntity = Employee.class)
	private Employee employee;

	public Raport() {
	}

	public Raport(long id, List<Task> list) {
		super();
		this.id = id;
		this.tasks = list;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
}
