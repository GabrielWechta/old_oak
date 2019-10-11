package com.harman.database.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "employee")
public class Employee implements Cloneable {

	@Id
	private String id;

	@NotEmpty
	@Column(unique = true)
	private String username;

	@NotEmpty
	private String password;

	private String authority;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "employee_authority", joinColumns = { @JoinColumn(name = "employee_id") }, inverseJoinColumns = {
			@JoinColumn(name = "authority_id") })
	public Set<Authority> authorities = new HashSet<>();

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Report> reports = new ArrayList<>();

	public Employee() {
	}

	public Employee(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public Employee(@NotEmpty String username, @NotEmpty String password, Authority authority) {
		this.username = username;
		this.password = password;
		this.authorities.add(authority);
		this.authority = authority.getName().toString();
	}

	public Employee(String id, @NotEmpty String username, @NotEmpty String password) {
		this.id = id;
		this.username = username;
		this.password = password;
	}

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

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String auth) {
		if (auth == "User") {
			authorities.add(new Authority(AuthorityType.ROLE_USER));
			this.authority = AuthorityType.ROLE_USER.toString();
		} else {
			authorities.add(new Authority(AuthorityType.ROLE_ADMIN));
			this.authority = AuthorityType.ROLE_ADMIN.toString();
		}
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return (Collection<? extends GrantedAuthority>) this.authorities;
	}

	public String getReportsAsString() {
		String reportsRepresenantion = "";
		for (Report r : this.reports) {
			reportsRepresenantion += ("(" + Long.toString(r.getId()) + " : ");
			reportsRepresenantion += (r.getDate() + ") | ");
		}
		return reportsRepresenantion;
	}

	public void setReports(List<Report> reports) {
		this.reports = reports;
	}

	public void addReport(Report report) {
		this.reports.add(report);
	}
}