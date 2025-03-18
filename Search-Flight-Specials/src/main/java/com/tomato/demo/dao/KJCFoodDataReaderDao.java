package com.tomato.demo.dao;

import com.tomato.demo.model.KJCFoodData;
import org.springframework.stereotype.Repository;

@Repository
public class KJCFoodDataReaderDao extends CSVDataReaderDao<KJCFoodData> {

    @Override
    protected boolean isValidData(String[] data) {
        return data.length >= 3;
    }

    @Override
    protected KJCFoodData parseData(String[] data) {
    	KJCFoodData food = new KJCFoodData();
        food.setCountry(data[0]);
        food.setFoodName(data[1]);
        food.setDescription(data[2]);
        return food;
    }
}