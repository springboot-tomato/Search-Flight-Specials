package com.tomato.demo.dao;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tomato.demo.model.IATAsearch;

@Repository
public class CSVDataReaderDao {

    public List<IATAsearch> readCSVFileData(String fileName) {

        List<IATAsearch> IATAsearchf = new ArrayList<>();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {

            if (inputStream == null) {
                throw new IllegalArgumentException("File not found: " + fileName);
            }

            String readLine;
            while ((readLine = br.readLine()) != null) {
                String[] data = readLine.split(",");

                if (data.length < 8) {
                    System.out.println("Skipping invalid line: " + readLine);
                    continue; // データが不足している場合はスキップ
                }

                String airportNmEng = data[0];
                String airportNmKr = data[1];
                String airportIATA = data[2];
                String airportICAO = data[3];
                String continents = data[4];
                String countryNmEng = data[5];
                String countryNmKr = data[6];
                String cityNmEng = data[7];

                IATAsearch s = new IATAsearch();

                s.setAirportNmEng(airportNmEng);
                s.setAirportNmKr(airportNmKr);                
                s.setCountryNmEng(countryNmEng);
                s.setCountryNmKr(countryNmKr);
                s.setAirportIATA(airportIATA);
                s.setAirportICAO(airportICAO);
                s.setContinents(continents);
                s.setCityNmEng(cityNmEng);

                // Add to list
                IATAsearchf.add(s);

                // Print parsed object
                System.out.println("Parsed IATAsearch object: " + s);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return IATAsearchf;
    }
}