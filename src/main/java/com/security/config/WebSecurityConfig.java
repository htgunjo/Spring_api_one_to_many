package com.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.security.filter.JWTLogInFilter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		String password = "123";
		String encryptdPassword = this.passwordEncoder().encode(password);
		System.out.printf("Encoded password for 123" + encryptdPassword);
		InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> manageConfig = auth.inMemoryAuthentication();

		UserDetails userOne = User.withUsername("tom").password(encryptdPassword).roles("USER").build();
		UserDetails userTwo = User.withUsername("jerry").password(encryptdPassword).roles("USER").build();

		manageConfig.withUser(userOne);
		manageConfig.withUser(userTwo);
		}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
		.antMatchers("/").permitAll()
		.antMatchers(HttpMethod.GET, "/login").permitAll()
		.antMatchers(HttpMethod.POST, "/login").permitAll()		
		.antMatchers(HttpMethod.PUT, "/login").permitAll()
		.antMatchers(HttpMethod.DELETE, "/login").permitAll()
		
		// need authentication
		.anyRequest().authenticated()
		.and()
				// Add the filter to get Token and authenticate
		.addFilterBefore(new JWTLogInFilter("/login", authenticationManager()),
						UsernamePasswordAuthenticationFilter.class);
	}		
		
		
}
