package com.example.springstarter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class}) //if not excluding will throw 401 error not authorized.since we are not including default filter token of Spring Boot.
@ServletComponentScan  //for @Component in filters
public class SpringStarterApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringStarterApplication.class, args);
	}

}
//SecurityAutoConfiguration.class -> this class is for including default filter tokens of Spring boot since now we are
// creating custom filter therefore excluding this.