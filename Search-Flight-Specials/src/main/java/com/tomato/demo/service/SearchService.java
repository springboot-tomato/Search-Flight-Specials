package com.tomato.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tomato.demo.dao.CSVDataReaderDao;
import com.tomato.demo.dao.CSVDataWriterDao;
import com.tomato.demo.model.IATAsearch;
import com.tomato.demo.util.SearchDataFilter;

@Service
public class SearchService {
	
	@Autowired
	private CSVDataReaderDao csvDataReaderDao;
	
	@Autowired
	private SearchDataFilter SearchDataFilter;
	
	@Autowired
	private CSVDataWriterDao csvDataWriterDao;
	
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
