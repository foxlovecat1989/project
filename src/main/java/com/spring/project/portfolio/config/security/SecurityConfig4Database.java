package com.spring.project.portfolio.config.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//@Configuration
//@EnableWebSecurity
public class SecurityConfig4Database extends WebSecurityConfigurerAdapter {
//	
//	@Autowired
//	DataSource dataSource;
	
//	@Bean
//	public PasswordEncoder getPasswordEncoder() {
//	    return new BCryptPasswordEncoder();
//	}
//	
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		
//		String myUsersByUsernameQuery = 
//				"select name, password, enabled from User where name = ?";
//		String myAuthoritiesByUsernameQuery = 
//				"select u.name, r.name from User u, Role r, Users_Roles ur"
//				+ " where u.id = ur.user_id and r.id=ur.role_id"
//				+ " and u.name = ?";
//		
//		auth.jdbcAuthentication().dataSource(dataSource)
//				.usersByUsernameQuery(myUsersByUsernameQuery)
//				.authoritiesByUsernameQuery(myAuthoritiesByUsernameQuery)
//				.passwordEncoder(getPasswordEncoder());
//	}
	
	
}