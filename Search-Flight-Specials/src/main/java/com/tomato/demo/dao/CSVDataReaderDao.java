package com.tomato.demo.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tomato.demo.IATAsearch;
import com.tomato.demo.model.Search;

@Repository
public class CSVDataReaderDao {

	public List<IATAsearch> readCSVFileData(String fileName) {
		
		List<IATAsearch> IATAsearchf = new ArrayList<>();
		
		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fileReader);
			String readLine = br.readLine();
			
			while(readLine != null) {
				String[] data = readLine.split(",");
				
				String airportNmEng = data[0];
				String airportNmKr = data[1];
				String airportIATA = data[2];
				String airportICAO = data[3];
				String continents = data[4];
				String countryNmEng = data[5];
				String countryNmKr = data[6];
				String cityNmEng = data[7];
			
				Search s = new Search();
				
				s.setCountryNmEng(countryNmEng);
				s.setCountryNmKr(countryNmKr);
				s.setAirportIATA(airportIATA);
				s.setAirportICAO(airportICAO);
				s.setContinents(continents);
				s.setCountryNmEng(countryNmEng);
				s.setCountryNmKr(countryNmKr);
				s.setCityNmEng(cityNmEng);
				
				//set in list
				IATAsearchf.add(s);
				//go to next line
				readLine = br.readLine();
			}
			
			br.close();
			fileReader.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return IATAsearchf;
	}

}
