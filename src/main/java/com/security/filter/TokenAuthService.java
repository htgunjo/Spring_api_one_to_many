package com.security.filter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Date;

public class TokenAuthService {
	
	 static final long EXPIRATIONTIME = 864_000_000; //10days..
	  static final String SECRET = "ThisisSecret";
	  static final String TOKEN_PREFIX = "Bearer";
	  static final String HEADER_STRING = "Authorization";
	  
	  
	  public static void addAuthentication(HttpServletResponse response, String userName) {
		
		String JWT = Jwts.builder().setSubject(userName)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
				.signWith(SignatureAlgorithm.HS512, SECRET).compact();
		
		response.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
		
	  }
	  
	  
	  public static Authentication getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(HEADER_STRING);
		if (token != null) {
		  //parse the token and verify the token is correct
		  String user = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
				  .getBody().getSubject();
		  return user != null ? new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList()) : null;
		}
		
		return null;
		
		
	  }
	  
	  
	}