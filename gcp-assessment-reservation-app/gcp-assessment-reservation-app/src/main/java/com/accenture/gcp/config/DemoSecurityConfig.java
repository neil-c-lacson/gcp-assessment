package com.accenture.gcp.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
public class DemoSecurityConfig extends WebSecurityConfigurerAdapter {

	// add a reference to our security data source
	
	@Autowired
	@Qualifier("securityDataSource")
	private DataSource securityDataSource;
		
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		// use jdbc authentication ... oh yeah!!!		
		auth.jdbcAuthentication().dataSource(securityDataSource);
		
	}
	
	@Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
			.antMatchers("/employees/showForm*").hasRole("STAFF")
			.antMatchers("/employees/save*").hasRole("STAFF")
			.antMatchers("/employees/delete").hasRole("STAFF")
			.antMatchers("/employees/**").hasAnyRole("STAFF", "CLIENT")
			
			.antMatchers("/rooms/showForm*").hasRole("STAFF")
			.antMatchers("/rooms/saveRoom*").hasRole("STAFF")
			.antMatchers("/rooms/deleteRoom").hasRole("STAFF")
			.antMatchers("/rooms/**").hasAnyRole("STAFF", "CLIENT")	
			
			.antMatchers("/reservations/showForm*").hasAnyRole("STAFF", "CLIENT")
			.antMatchers("/reservations/saveReservation*").hasAnyRole("STAFF", "CLIENT")
			.antMatchers("/reservations/deleteReservation").hasAnyRole("STAFF", "CLIENT")
			.antMatchers("/reservations/**").hasAnyRole("STAFF", "CLIENT")		
			.antMatchers("/resources/**").permitAll()
			.and()
			.formLogin()
				.loginPage("/showMyLoginPage")
				.loginProcessingUrl("/authenticateTheUser")
				.successHandler(customAuthenticationSuccessHandler)
				.permitAll()
			.and()
			.logout().permitAll()
			.and()
			.exceptionHandling().accessDeniedPage("/access-denied");
		
	}
	
		
}






