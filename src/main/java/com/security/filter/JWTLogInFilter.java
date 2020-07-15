package com.security.filter;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class JWTLogInFilter extends AbstractAuthenticationProcessingFilter {

	 public JWTLogInFilter(String url, AuthenticationManager authenticationManager) {
			super(new AntPathRequestMatcher(url));
			setAuthenticationManager(authenticationManager);
		  }
		  
		  
		  @Override
		  public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
				  throws AuthenticationException, IOException, ServletException {
			
		    String userName = httpServletRequest.getParameter("username");
		    String passWord = httpServletRequest.getParameter("password");
			System.out.printf("JWT LOGIN username and password: %s,%s", userName, passWord);
			System.out.println();
			
			return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(userName, passWord,
					Collections.emptyList()));
			
		  }
		  
		  @Override
		  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain
				  , Authentication authResult) throws IOException, ServletException {
		  
			System.out.println("JWTLoginFilter.successfulAuthentication:");
			// Write Authorization to Headers of Response.
			TokenAuthService.addAuthentication(response, authResult.getName());
			String authorizationString = response.getHeader("Authorization");
			System.out.println("Authorization String=" + authorizationString);
		  }
		  
		  
		}