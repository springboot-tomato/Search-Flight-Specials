package com.tomato.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.amadeus.Amadeus;

import lombok.Getter;
import lombok.Setter;

@Component
@PropertySource("classpath:application.properties")
@Getter
@Setter
public class AmadeusConfig {
	@Value("${amadeusAPI.key}")
	private String key;
	
	@Value("${amadeusAPI.secret}")
	private String secret;
	
	@Bean
    public Amadeus amadeus() {
        return Amadeus.builder(key, secret).build();
    }
}