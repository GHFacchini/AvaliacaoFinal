package com.compasso.duvidas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class DuvidasApplication {

	public static void main(String[] args) {
		SpringApplication.run(DuvidasApplication.class, args);
	}

}
