package com.tomato.demo.service;

import com.tomato.demo.dao.KJCFoodDataReaderDao;
import com.tomato.demo.dao.KJCCityDataReaderDao;
import com.tomato.demo.model.KJCFoodData;
import com.tomato.demo.model.KJCCityData;
import org.springframework.stereotype.Service;
import com.tomato.demo.model.IATAsearch; // CSVファイルロード確認のため、IATAsearch import 追加

import java.util.List;

@Service
public class CSVProcessingService {

    private final KJCFoodDataReaderDao kjcFoodDataReaderDao;
    private final KJCCityDataReaderDao kjcCityDataReaderDao;
    private final CSVtoIATAService csvtoIATAService; // CSVtoIATAService 注入

    public CSVProcessingService(KJCFoodDataReaderDao kjcFoodDataReaderDao, KJCCityDataReaderDao kjcCityDataReaderDao, CSVtoIATAService csvtoIATAService) {
        this.kjcFoodDataReaderDao = kjcFoodDataReaderDao;
        this.kjcCityDataReaderDao = kjcCityDataReaderDao;
        this.csvtoIATAService = csvtoIATAService;
    }

    public void processAllCSVFiles() {
        // IATA_list.csv処理はCSVtoIATAServiceに委任
    	List<IATAsearch> iataList = csvtoIATAService.doProcess();

    	// KJCFoodData処理
        List<KJCFoodData> kjcFoodList = kjcFoodDataReaderDao.readCSVFileData("KJCfood_data.csv");
        
        // KJCCityData処理
        List<KJCCityData> kjcCityList = kjcCityDataReaderDao.readCSVFileData("KJCcity_data.csv");
        
        // 파일 읽기 성공 여부 확인
        boolean iataSuccess = (iataList != null && !iataList.isEmpty());
        boolean foodSuccess = (kjcFoodList != null && !kjcFoodList.isEmpty());
        boolean citySuccess = (kjcCityList != null && !kjcCityList.isEmpty());

        // 処理結果＆データサイズ出力
        System.out.println("IATA_list.csv 읽기 성공: " + iataSuccess + ", IATA DATA list size: " + (iataList != null ? iataList.size() : 0));
        System.out.println("KJCfood_data.csv 읽기 성공: " + foodSuccess + ", KJC Food list size: " + (kjcFoodList != null ? kjcFoodList.size() : 0));
        System.out.println("KJCcity_data.csv 읽기 성공: " + citySuccess + ", KJC City list size: " + (kjcCityList != null ? kjcCityList.size() : 0)); 
    }
}