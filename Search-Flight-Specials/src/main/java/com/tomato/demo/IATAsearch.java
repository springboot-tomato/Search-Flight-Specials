package com.tomato.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.thymeleaf.spring6.processor.SpringActionTagProcessor;

import com.tomato.demo.service.SearchService;

@SpringBootApplication
public class IATAsearch {

	public static void main(String[] args) {
	
	
		ConfigurableApplicationContext context = SpringApplication.run(IATAsearch.class, args);

		SearchService searchService = context.getBean(SearchService.class);
	
		searchService.doProcess("IATA_list.csv");
	
	}
	
}