package com.compasso.duvidas.config.db;

import java.io.IOException;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.compasso.duvidas.services._DBService;

@Configuration
@Profile("prod")
public class ConfigurationDBProd {
	
	@Autowired
	private _DBService dbService;
	
    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String strategy;

    @Bean
    public boolean instatiateDatabase() throws ParseException, IOException {
    	dbService.instantiateProdDatabase();
        return true;
    }

}
