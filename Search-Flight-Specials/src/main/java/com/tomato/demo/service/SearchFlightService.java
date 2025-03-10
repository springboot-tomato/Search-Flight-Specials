package com.tomato.demo.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tomato.demo.repository.SearchFlightRepository;

@Service
public class SearchFlightService {
	public SearchFlightRepository parseFlightOffers(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, SearchFlightRepository.class);
	}
}
