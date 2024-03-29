package com.harman.database.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.harman.database.entities.Employee;
import com.harman.database.repositories.EmployeeRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

	@Autowired
	private EmployeeRepository employeeRepository;

	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Employee employee = employeeRepository.findByUsername(username);

		if (employee == null) {
			log.info("Employee is null!");
			throw new UsernameNotFoundException("Employee not found.");
		}

		return new UserDao(employee);
	}
}
