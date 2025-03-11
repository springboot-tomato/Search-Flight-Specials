package com.tomato.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.tomato.demo.service.SearchService;

@SpringBootApplication
public class IATAsearchApplication {

	public static void main(String[] args) {
	
	
		ConfigurableApplicationContext context = SpringApplication.run(IATAsearchApplication.class, args);

		SearchService searchService = context.getBean(SearchService.class);
	
		searchService.doProcess("IATA_list.csv");
	
	}
	
}