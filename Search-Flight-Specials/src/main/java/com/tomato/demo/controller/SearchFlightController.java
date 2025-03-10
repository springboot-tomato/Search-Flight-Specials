package com.tomato.demo.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tomato.demo.repository.SearchFlightRepository;
import com.tomato.demo.service.SearchFlightService;

@RestController
@RequestMapping("/api")
public class SearchFlightController {

	private final SearchFlightService searchFlightService;

	public SearchFlightController(SearchFlightService searchFlightService) {
		this.searchFlightService = searchFlightService;
	}

	@PostMapping("/search-results")
	public ResponseEntity<SearchFlightRepository> processResults(@RequestBody Map<String, Object> results) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(results);

			SearchFlightRepository flightOffers = searchFlightService.parseFlightOffers(json);
			
			//データ処理を入れる

			return ResponseEntity.ok(flightOffers);
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
