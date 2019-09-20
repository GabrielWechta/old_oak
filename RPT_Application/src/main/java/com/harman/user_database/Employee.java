package com.harman.user_database;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.security.core.GrantedAuthority;

import com.harman.report_database.Report;

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

	private String authority;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "employee_authority", joinColumns = { @JoinColumn(name = "employee_id") }, inverseJoinColumns = {
			@JoinColumn(name = "authority_id") })
	public Set<Authority> authorities = new HashSet<>();

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Report> raports = new ArrayList<>();

	public Employee() {
		super();
	}

	public Employee(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public Employee(@NotEmpty String username, @NotEmpty String password, Authority authority) {
		super();
		this.username = username;
		this.password = password;
		this.authorities.add(authority);

		if (authority.getName().equals(AuthorityType.ROLE_USER)) {
			this.authority = AuthorityType.ROLE_USER.toString();
		} else {
			this.authority = AuthorityType.ROLE_ADMIN.toString();
		}
	}

	public Employee(@NotEmpty String username, @NotEmpty String password, Authority authority, List<Report> raports) {
		super();
		this.username = username;
		this.password = password;
		this.authorities.add(authority);
		this.raports = raports;

		if (authority.getName().equals(AuthorityType.ROLE_USER)) {
			this.authority = AuthorityType.ROLE_USER.toString();
		} else {
			this.authority = AuthorityType.ROLE_ADMIN.toString();
		}
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

	public void setAuthority(AuthorityType auth) {
		this.authority = auth.name();
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return (Collection<? extends GrantedAuthority>) this.authorities;
	}

	public List<Report> getRaports() {
		return raports;
	}

	public void setRaports(List<Report> raports) {
		this.raports = raports;
	}
}