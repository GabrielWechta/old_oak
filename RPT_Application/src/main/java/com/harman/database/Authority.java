package com.harman.database;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "authority")
public class Authority {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;

	@Enumerated(EnumType.STRING)
	private AuthorityType name;

	public Authority() {
	}

	public Authority(AuthorityType name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public AuthorityType getName() {
		return name;
	}

	public void setName(AuthorityType name) {
		this.name = name;
	}
}
