// Adapted from Lecture 10 Spring Boot security notes

package com.sales.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authorizeRequests().antMatchers("/showProducts", "/showCustomers", "/showOrders", "/addOrder",
				"/addProduct", "/addCustomer").hasRole("USER").and().formLogin();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)

			throws Exception {
		auth.inMemoryAuthentication().withUser("user").password("user").roles("USER");
	}

}
