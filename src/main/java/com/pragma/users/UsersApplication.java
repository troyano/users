package com.pragma.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class UsersApplication {

	public static void main(final String[] args) {
		SpringApplication.run(UsersApplication.class, args);
	}
}
