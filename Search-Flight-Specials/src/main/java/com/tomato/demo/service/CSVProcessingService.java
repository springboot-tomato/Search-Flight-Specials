package com.tomato.demo.service;

import org.springframework.stereotype.Service;
import com.tomato.demo.model.IATAsearch; // CSVファイルの一部としてIATAsearchインポート

import java.util.List;

/**
 * CSVファイル処理サービス
 *
 */
@Service
public class CSVProcessingService {

    private final CSVtoIATAService csvtoIATAService; // CSVtoIATAService 注入

    public CSVProcessingService(CSVtoIATAService csvtoIATAService) {
        this.csvtoIATAService = csvtoIATAService;
    }

    /**
     * すべてのCSVファイルを処理するメソッド
     * 
     */
    public void processAllCSVFiles() {
        // IATA_list.csv処理はCSVtoIATAServiceに委任
    	List<IATAsearch> iataList = csvtoIATAService.doProcess();
    }
}