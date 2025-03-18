package com.tomato.demo.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class IATACSVConfig implements CSVConfig {
    @Override
    public int getMinColumns() {
        return 8;
    }

    @Override
    public Map<String, Integer> getColumnMapping() {
        Map<String, Integer> mapping = new HashMap<>();
        mapping.put("airportNmEng", 0);
        mapping.put("airportNmKr", 1);
        mapping.put("airportIATA", 2);
        mapping.put("airportICAO", 3);
        mapping.put("continents", 4);
        mapping.put("countryNmEng", 5);
        mapping.put("countryNmKr", 6);
        mapping.put("cityNmEng", 7);
        return mapping;
    }
}