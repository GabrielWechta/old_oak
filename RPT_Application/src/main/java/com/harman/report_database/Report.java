package com.harman.report_database;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.harman.user_database.Employee;

@Entity
@Table(name = "report")
public class Report {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private LocalDate date;
	private String employeeUsername;
	private String wwb;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Task> tasks = new ArrayList<Task>();

	@ManyToOne
	private Employee employee;

	public Report() {
	}

	public Report(List<Task> list) {
		this.tasks = list;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTasks() {
		String taskRepresentation = "";
		for (Task t : this.tasks) {
			taskRepresentation += ("(" + Long.toString(t.getId()) + " : ");
			taskRepresentation += (t.getWwbType().toString() + ") | ");
		}
		return taskRepresentation;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
		this.setEmployeeUsername(this.employee.getUsername());
	}

	public void addTask(Task task) {
		this.tasks.add(task);
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getEmployeeUsername() {
		return employeeUsername;
	}

	public void setEmployeeUsername(String employeeUsername) {
		this.employeeUsername = employeeUsername;
	}

	public String getWwb() {
		int f = 0, b = 0, r = 0;
		DecimalFormat df = new DecimalFormat("##.##%");
		for (Task t : tasks) {
			switch (t.getWwbType()) {
			case FEAUTURE:
				f++;
				break;
			case BUG:
				b++;
				break;
			case REFACTOR:
				r++;
				break;
			default:
				break;
			}
		}
		if (f + b + r > 0) {
			return df.format((double) (f + (b * 0.8) + (r * 0.6)) / (double) (f + b + r));
		} else {
			return df.format(0.0);
		}
	}

	public void setWwb(String wwb) {
		this.wwb = wwb;
	}
}
