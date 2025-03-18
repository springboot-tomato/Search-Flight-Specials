package com.tomato.demo.dao;

import com.tomato.demo.model.KJCCityData;
import org.springframework.stereotype.Repository;

@Repository
public class KJCCityDataReaderDao extends CSVDataReaderDao<KJCCityData> {

    @Override
    protected boolean isValidData(String[] data) {
        return data.length >= 4;
    }

    @Override
    protected KJCCityData parseData(String[] data) {
    	KJCCityData city = new KJCCityData();
        city.setCountry(data[0]);
        city.setCityName(data[1]);
        city.setDescription(data[2]);
        city.setPopulation(data[3]); // populationを Stringで設定
        return city;
    }
}