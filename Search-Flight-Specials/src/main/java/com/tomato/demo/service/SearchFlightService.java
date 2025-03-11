package com.tomato.demo.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tomato.demo.repository.SearchFlightRepository;
import com.tomato.demo.repository.SearchFlightRepository.*;


@Service
public class SearchFlightService {
	public SearchFlightRepository parseFlightOffers(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try 
        {
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
                		Location carrier = repo.getDictionaries().get(carrierCode);
                		System.out.println(carrier.getCityCode());
                		//出発空港
                		segment.getDeparture().getTerminal();
                		//出発時間
                		String departureTime = segment.getDeparture().getAt().split("T")[1];
                		//到着空港
                		segment.getArrival().getTerminal();
                		//到着時間
                		String arrivalTime = segment.getArrival().getAt().split("T")[1];
                		
                	}
                }
            }
        }
        catch(JsonProcessingException e)
        {
        	e.printStackTrace();
        }
        return objectMapper.readValue(json, SearchFlightRepository.class);
	}
}
