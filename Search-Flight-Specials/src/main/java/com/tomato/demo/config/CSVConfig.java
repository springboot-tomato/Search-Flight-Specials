package com.tomato.demo.config;

import java.util.Map;

public interface CSVConfig {
    int getMinColumns();
    Map<String, Integer> getColumnMapping();
}
