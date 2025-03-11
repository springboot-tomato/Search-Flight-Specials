package com.tomato.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

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
}