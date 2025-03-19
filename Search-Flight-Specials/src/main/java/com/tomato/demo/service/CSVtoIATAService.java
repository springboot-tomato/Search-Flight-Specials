package com.tomato.demo.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tomato.demo.dao.CSVDataWriterDao;
import com.tomato.demo.dao.IATADataReaderDao;
import com.tomato.demo.model.IATAsearch;
import com.tomato.demo.util.SearchDataFilter;

import jakarta.annotation.PostConstruct;

/**
 * CSVファイルからIATAデータを処理し検索するサービス
 * 
 * このクラスはCSVファイルからIATAデータを読み込み、キャッシュし、キーワードベースの検索機能を提供する。
 * 
 */
@Service
@RestController
public class CSVtoIATAService {

	@Autowired
	private IATADataReaderDao iataDataReaderDao;

	@Autowired
	private SearchDataFilter SearchDataFilter;

	@Autowired
	private CSVDataWriterDao csvDataWriterDao;
    
    private List<IATAsearch> cachedIATAData;

    // 初期化時にデータを読み込む
    @PostConstruct
    public void initialize() {
        cachedIATAData = iataDataReaderDao.readCSVFileData("IATA_list.csv");
    }

	// キーワードでIATAデータを検索
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

    // データ処理のメインロジック
	public List<IATAsearch> doProcess() {
        // CSVファイルからデータを読み込む
		List<IATAsearch> IATAseachf = iataDataReaderDao.readCSVFileData("IATA_list.csv");
		
        // 検索データをフィルタリング
		List<IATAsearch> IATAsearchData = SearchDataFilter.filterSearchData(IATAseachf);

//		// フィルタリングされたデータを別のCSVファイルに保存
//		String status = csvDataWriterDao.writeCSVFileData(IATAseachf);

//		System.out.println("CSV File Writing Status: " + status);
		
		return IATAseachf; // IATAseachfを戻り値にする。
	}
    
    // IATAデータを検索するAPIエンドポイントマッピング
    @GetMapping("/api/search-iata")
    public ResponseEntity<List<Map<String, String>>> searchIATA(
            @RequestParam("keyword") String keyword) {
        List<Map<String, String>> results = searchIATAByKeyword(keyword); // キーワードからIATAデータを検索
        if (results == null || results.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 検索結果がなければ404 Not Foundを返却
        }
        return new ResponseEntity<>(results, HttpStatus.OK); // 検索結果をJSON形式で返却
    }
    
}