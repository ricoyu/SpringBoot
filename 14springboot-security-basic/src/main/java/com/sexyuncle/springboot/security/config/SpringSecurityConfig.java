package com.sexyuncle.springboot.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.sexyuncle.springboot.security.service.MyBasicAuthenticationEntryPoint;

/**
 * For user “admin” :
 * 
 * Able to access /admin page
 * Unable to access /user page, redirect to 403 access denied page.
 * 
 * For user “user” :
 * 
 * able to access /user page
 * unable to access /admin page, redirect to 403 access denied page.
 * <p>
 * Copyright: Copyright (c) 2018-04-20 16:09
 * <p>
 * Company: DataSense
 * <p>
 * @author Rico Yu	ricoyu520@gmail.com
 * @version 1.0
 * @on
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
    private MyBasicAuthenticationEntryPoint authenticationEntryPoint;

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.authorizeRequests()
				.antMatchers("/securityNone").permitAll()
				.antMatchers("/", "/home", "/about").permitAll()
				.antMatchers("/", "/js/**", "/css/**").permitAll()
				.antMatchers("/admin/**").hasAnyRole("ADMIN")
				.antMatchers("/user/**").hasAnyRole("USER")
				.anyRequest().authenticated()
				.and()
				.httpBasic()
				.authenticationEntryPoint(authenticationEntryPoint);
//				.formLogin()
//				.loginPage("/login")
//				.permitAll()
//				.and()
//				.logout()
//				.permitAll()
//				.and()
//				.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
	}

	// create two users, admin and user
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
				.withUser("rico").password("test123456").authorities("ROLE_USER");
	}

}
