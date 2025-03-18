package com.tomato.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.tomato.demo.service.CSVProcessingService;

@SpringBootApplication
public class IATAsearchApplication {

	public static void main(String[] args) {
	
        ConfigurableApplicationContext context = SpringApplication.run(IATAsearchApplication.class, args);
        CSVProcessingService processingService = context.getBean(CSVProcessingService.class); // CSVProcessingServiceのBeanのインポート
        processingService.processAllCSVFiles(); // すべてのCSVファイル処理
	
	}
	
}