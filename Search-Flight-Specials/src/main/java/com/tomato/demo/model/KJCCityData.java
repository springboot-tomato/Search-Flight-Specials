package com.tomato.demo.model;

import lombok.Data;

@Data
public class KJCCityData {

    private String country;
    private String cityName;
    private String description;
    private String population; // populationを Stringで 維持
	
}
