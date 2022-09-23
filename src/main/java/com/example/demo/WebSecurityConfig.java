package com.example.authenticatingldap;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService myUserDetailsService;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(myUserDetailsService);
	}
	/*
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.anyRequest().fullyAuthenticated()
				.and()
			.formLogin()
			.and()
			.cors().disable()
			.csrf().disable();
	}
*/
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeRequests()
			.antMatchers("/authenticate")
			.permitAll()
			.anyRequest()
			.authenticated();
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.ldapAuthentication()
				.userDnPatterns("uid={0},ou=people")
				.groupSearchBase("ou=groups")
				.contextSource()
					.url("ldap://localhost:8389/dc=springframework,dc=org")
					.and()
				.passwordCompare()
					.passwordEncoder(new BCryptPasswordEncoder())
					.passwordAttribute("userPassword");
	}
	
}
