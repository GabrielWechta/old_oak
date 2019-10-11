package com.harman.database.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "task")
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String type; // enum?
	private String name;
	private String placement; // enum?
	private String description;
	private WwbType wwbType;

	@ManyToOne
	private Report report;

	public Task() {
	}

	public Task(String type, String name, String placement, String description, WwbType wwbType) {
		super();
		this.type = type;
		this.name = name;
		this.placement = placement;
		this.description = description;
		this.wwbType = wwbType;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPlacement() {
		return placement;
	}

	public void setPlacement(String placement) {
		this.placement = placement;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Report getReport() {
		return report;
	}

	public void setReport(Report report) {
		this.report = report;
	}

	public WwbType getWwbType() {
		return wwbType;
	}

	public void setWwbType(WwbType wwbType) {
		this.wwbType = wwbType;
	}
}
