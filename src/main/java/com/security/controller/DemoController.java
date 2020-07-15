package com.security.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@RestController
public class DemoController {

//	@GetMapping("hello")
//	public  String sayHello() {
//		return "Hello from Spring Auth Actuator API";
//	}
//	
//	@GetMapping("/api")
//	public  String sayWhatsUp() {
//		return "Hello from API methood";
//	}

	@GetMapping("/")
	public String healtCheck(HttpServletRequest request) {

		String contextPath = request.getContextPath();
		String host = request.getServerName();
		String endpoint = "/actuator";

		StringBuilder sb = new StringBuilder();
		sb.append("<h2>Spring Boot Actuator</h2>");
		sb.append("<h2>Below is path that provide app monitoring services</h2>");
		sb.append("<ul>");

		// http://localhost:4040/actuator
		String url = "http://" + host + ":4040" + contextPath + endpoint;
		sb.append("<li><a href=" + url + ">" + url + "<a></li>");

		sb.append("</ul>");

		return sb.toString();
	}

	@GetMapping("/shutdown")
	  public String callShutDown() {
		
		//URL for application to shutdown
		String url = "http://localhost:4040/actuator/shutdown";
		
		HttpHeaders headers = new HttpHeaders();
		
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> request = new HttpEntity<>("", headers);
		
		//Send the request for shoutdown
		String shut = restTemplate.postForObject(url, request, String.class);
		
		return "Result : " + shut;
		
	}
	  }
