package com.harman.raport_database;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "task")
public class Task {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long lp;
	
	private String type; //enum?
	private String name;
	private String placement; //enum?
	private String description;
	
	@ManyToOne
	@JoinColumn
	private Raport raport;
	
	public Task() {
	}
	
	public Task(long lp, String type, String name, String placement, String description) {
		super();
		this.lp = lp;
		this.type = type;
		this.name = name;
		this.placement = placement;
		this.description = description;
	}
	public long getLp() {
		return lp;
	}
	public void setLp(long lp) {
		this.lp = lp;
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

	
}
