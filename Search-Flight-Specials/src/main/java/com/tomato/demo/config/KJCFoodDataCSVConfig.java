package com.tomato.demo.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class KJCFoodDataCSVConfig implements CSVConfig {
    @Override
    public int getMinColumns() {
        return 3;
    }

    @Override
    public Map<String, Integer> getColumnMapping() {
        Map<String, Integer> mapping = new HashMap<>();
        mapping.put("country", 0);
        mapping.put("foodName", 1);
        mapping.put("description", 2);
        return mapping;
    }
}
