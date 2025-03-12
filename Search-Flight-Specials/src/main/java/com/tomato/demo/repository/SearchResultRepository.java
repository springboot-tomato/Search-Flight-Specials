package com.tomato.demo.repository;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class SearchResultRepository {
	private List<Map<String, Object>> flightDetails;
}
