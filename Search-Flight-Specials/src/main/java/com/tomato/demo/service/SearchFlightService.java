package com.tomato.demo.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tomato.demo.repository.SearchFlightRepository;
import com.tomato.demo.repository.SearchFlightRepository.*;
import com.tomato.demo.repository.SearchResultRepository;

@Service
public class SearchFlightService {
	public SearchResultRepository parseFlightOffers(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> res = new ArrayList<>();
        try 
        {
        	Map<String, Object> map = new HashMap<>();
        	//必要なデータだけ取得する
        	//JacksonのObjectMapperを使って値をrepoに入れる
        	SearchFlightRepository repo = objectMapper.readValue(json, SearchFlightRepository.class);
        	//SearchFlightRepository→FlightOffer
        	for (FlightOffer offer : repo.getFlightOffers()) {
        		//FlightOffer→Itinerary
                for(Itinerary itinerary : offer.getItineraries())
                {
                	//Itinerary→Segment
                	for(Segment segment : itinerary.getSegments())
                	{
                		//航空会社
                		String carrierCode = segment.getCarrierCode();
                		String carrier = (String)repo.getDictionaries().get("carriers").getAdditionalProperties().get(carrierCode);
                		//出発空港
                		String departureTerminal = segment.getDeparture().getIataCode();
                		//出発時間
                		String departureTime = segment.getDeparture().getAt().split("T")[1];
                		//到着空港
                		String arrivalTerminal = segment.getArrival().getIataCode();
                		//到着時間
                		String arrivalTime = segment.getArrival().getAt().split("T")[1];
                		//金額
                		double fee = offer.getPrice().getGrandTotal();
                		map.put("carrier", carrier);
                		map.put("departureTerminal", departureTerminal);
                		map.put("departureTime", departureTime);
                		map.put("arrivalTerminal", arrivalTerminal);
                		map.put("arrivalTime", arrivalTime);
                		map.put("fee", fee);
                		res.add(map);
                		//Reference型なので、アドレスを更新するため、newする
                		map = new HashMap<>();
                	}
                }
            }
        }
        catch(JsonProcessingException e)
        {
        	e.printStackTrace();
        }
        //結果をRepoに入れる
        SearchResultRepository resultRepo = new SearchResultRepository();
        resultRepo.setFlightDetails(res);
        return resultRepo;
	}
}
