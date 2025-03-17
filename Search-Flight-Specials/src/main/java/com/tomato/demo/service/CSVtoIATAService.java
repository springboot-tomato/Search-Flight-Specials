package com.tomato.demo.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tomato.demo.dao.CSVDataReaderDao;
import com.tomato.demo.dao.CSVDataWriterDao;
import com.tomato.demo.model.IATAsearch;
import com.tomato.demo.util.SearchDataFilter;

import jakarta.annotation.PostConstruct;

@Service
public class CSVtoIATAService {
	
	@Autowired
	private CSVDataReaderDao csvDataReaderDao;
	
	@Autowired
	private SearchDataFilter SearchDataFilter;
	
	@Autowired
	private CSVDataWriterDao csvDataWriterDao;
    
    private List<IATAsearch> cachedIATAData;

    @PostConstruct
    public void initialize() {
        cachedIATAData = csvDataReaderDao.readCSVFileData("IATA_list.csv");
    }

    public List<Map<String, String>> searchIATAByKeyword(String keyword) {
        return cachedIATAData.stream()
            .filter(iata -> iata.getAirportNmEng().toLowerCase().contains(keyword.toLowerCase()) ||
                            iata.getAirportNmKr().contains(keyword) ||
                            iata.getCityNmEng().toLowerCase().contains(keyword.toLowerCase()))
            .map(iata -> Map.of(
                "label", iata.getAirportNmEng() + " - " + iata.getAirportNmKr() + " (" + iata.getCityNmEng() + ")",
                "value", iata.getAirportIATA()
            ))
            .collect(Collectors.toList());
    }
	
	public void doProcess(String fileName) {
		//write a business logic
		
		//read Data from CSV file
		List<IATAsearch> IATAseachf = csvDataReaderDao.readCSVFileData(fileName);
		
		//Filter Search Data
		List<IATAsearch> IATAsearchData = SearchDataFilter.filterSearchData(IATAseachf);

		//Store Filter Data in another CSV file
		String status = csvDataWriterDao.writeCSVFileData(IATAseachf);
		
		System.out.println(status);
	}

}
