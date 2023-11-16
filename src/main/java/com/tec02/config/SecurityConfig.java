package com.tec02.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	@Autowired
	private final AuthenticationProvider authenticationProvider;
	@Autowired
	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
	      .csrf().disable()
	      .authorizeHttpRequests()
	      .requestMatchers( HttpMethod.POST)
	      .permitAll()
	      .requestMatchers( HttpMethod.GET)
	      .permitAll()
	      .anyRequest()
	      .authenticated()
	      .and()
	      .sessionManagement()
	      .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	      .and()
	      .authenticationProvider(authenticationProvider)
	      .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	    return http.build();
	}

	@Bean
	static RoleHierarchy roleHierarchy() {
	    RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
	    hierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");
	    return hierarchy;
	}

	@Bean
	static MethodSecurityExpressionHandler methodSecurityExpressionHandler(RoleHierarchy roleHierarchy) {
		DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
		expressionHandler.setRoleHierarchy(roleHierarchy);
		return expressionHandler;
	}
	
}
