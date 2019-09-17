package com.harman.database;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "employee")
public class Employee implements Cloneable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;

	@NotEmpty
	@Column(unique = true)
	private String username;

	@NotEmpty
	private String password;

	public Employee() {
		super();
	}

	public Employee(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "employee_authority", joinColumns = { @JoinColumn(name = "employee_id") }, inverseJoinColumns = {
			@JoinColumn(name = "authority_id") })

	private Set<Authority> authorities = new HashSet<>();

	@Override
	public Employee clone() throws CloneNotSupportedException {
		return (Employee) super.clone();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@SuppressWarnings("unchecked")
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return (Collection<? extends GrantedAuthority>) this.authorities;
	}
}