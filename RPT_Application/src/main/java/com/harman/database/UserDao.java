package com.harman.database;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserDao implements UserDetails {
	
    private Employee employee;
    
	public UserDao(Employee employee) {
        this.employee = employee;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return employee.getAuthorities().stream().map(authority -> new SimpleGrantedAuthority(authority.toString())).collect(Collectors.toList());
    }
    public String getId() {
        return employee.getId();
    }
    @Override
    public String getPassword() {
        return employee.getPassword();
    }
    @Override
    public String getUsername() {
        return employee.getUsername();
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
    public Employee getUserDetails() {
        return employee;
    }
}