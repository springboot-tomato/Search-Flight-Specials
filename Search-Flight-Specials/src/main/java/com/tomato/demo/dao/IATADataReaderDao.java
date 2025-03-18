package com.tomato.demo.dao;

import org.springframework.stereotype.Repository;

import com.tomato.demo.model.IATAsearch;

@Repository
public class IATADataReaderDao extends CSVDataReaderDao<IATAsearch> {

    @Override
    protected boolean isValidData(String[] data) {
        return data.length >= 8;
    }

    @Override
    protected IATAsearch parseData(String[] data) {
        IATAsearch s = new IATAsearch();
        s.setAirportNmEng(data[0]);
        s.setAirportNmKr(data[1]);
        s.setAirportIATA(data[2]);
        s.setAirportICAO(data[3]);
        s.setContinents(data[4]);
        s.setCountryNmEng(data[5]);
        s.setCountryNmKr(data[6]);
        s.setCityNmEng(data[7]);
        return s;
    }
}