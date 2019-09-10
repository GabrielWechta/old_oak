package com.harman;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.harman.utils.SecurityUtils;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private static final String LOGIN_PROCESSING_URL = "/login";
	private static final String LOGIN_FAILURE_URL = "/login?error"; // 
	private static final String LOGIN_URL = "/login";
	private static final String LOGOUT_SUCCESS_URL = "/login";
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception { // 
	    return super.authenticationManagerBean();
	}

	@Bean
	public CustomRequestCache requestCache() { // 
	     return new CustomRequestCache();
	}

	@Bean
	@Override
	public UserDetailsService userDetailsService() {
		UserDetails user =
				User.withUsername("user")
						.password("{noop}password")
						.roles("USER")
						.build();

		return new InMemoryUserDetailsManager(user);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    // Not using Spring CSRF here to be able to use plain HTML for the login page
	    http.csrf().disable() // 

	            // Register our CustomRequestCache that saves unauthorized access attempts, so
	            // the user is redirected after login.
	            .requestCache().requestCache(new CustomRequestCache()) // 

	            // Restrict access to our application.
	            .and().authorizeRequests()

	            // Allow all flow internal requests.
	            .requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll() // 

	            // Allow all requests by logged in users.
	            .anyRequest().authenticated() // 

	            // Configure the login page.
	            .and().formLogin().loginPage("/" + LoginView.ROUTE).permitAll()
	            .failureUrl(LOGIN_FAILURE_URL)

	            // Configure logout
	            .and().logout().logoutSuccessUrl(LOGOUT_SUCCESS_URL);
	}
	
	/**
	 * Allows access to static resources, bypassing Spring security.
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(
				"/VAADIN/**",
				"/favicon.ico",
				"/robots.txt",
				"/manifest.webmanifest",
				"/sw.js",
				"/offline-page.html",
				"/frontend/**",
				"/webjars/**",
				"/frontend-es5/**",
				"/frontend-es6/**");
	}
}
