package com.cognixia.jump.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cognixia.jump.filter.JwtRequestFilter;

@Configuration
public class SecurityConfiguration {
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	JwtRequestFilter jwtRequestFilter;
	
	// Authentication
	@Bean
	protected UserDetailsService userDetailsService() {
		
		return userDetailsService;
	}
	
	
	// Authorization
	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http.csrf().disable()
			.authorizeRequests()
			.antMatchers(HttpMethod.POST, "/api/user").permitAll()
			.antMatchers(HttpMethod.PUT, "/api/user").permitAll() // TODO limit to principal
			.antMatchers(HttpMethod.PUT, "/api/user/user-obj").access("hasRole('ADMIN')")
			.antMatchers(HttpMethod.GET, "/api/games").permitAll()
			.antMatchers(HttpMethod.POST, "/api/games").access("hasRole('ADMIN')")
			.antMatchers(HttpMethod.PUT, "/api/games").access("hasRole('ADMIN')")
			.antMatchers(HttpMethod.DELETE, "/api/games").access("hasRole('ADMIN')")
			.antMatchers(HttpMethod.PUT, "/api/user/user-obj").denyAll()
			.antMatchers("/authenticate").permitAll()	// anyone can create a JWT w/o needing to have a JWT first
			.anyRequest().authenticated()	// all APIs, you have to have a user account
			.and()
			.sessionManagement().sessionCreationPolicy( SessionCreationPolicy.STATELESS );
			 // tell spring security to NOT create sessions
		
		// request will go through many filters, but typically the FIRST filter it checks is the one for username & password
		// however, we will set it up, that our JWT filter gets checked first, or else the authentication will fail, since spring security
		// won't know where to find the username & password
		
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class );
		
		
		return http.build();
	}
	
	// manage password encoding
	@Bean
	protected PasswordEncoder encoder() {
		
		// plain text encoder -> encode/encrypt the password
		//return NoOpPasswordEncoder.getInstance();
		
		// encrypt the password with the bcrypt algorithm
		return new BCryptPasswordEncoder();
		
	}
	
	// load in the encoder & user details service that are needed for security to do authentication & authorization
	@Bean
	protected DaoAuthenticationProvider authenticationProvider() {
		
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(); 
		
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder( encoder() );
		
		return authProvider;
	}
	
	
	// can autowire and access the authentication manager (manages authentication (login) of our project)
	@Bean
	protected AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	
}















