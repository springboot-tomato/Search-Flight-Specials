package com.tomato.demo.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;

@Data
public class SearchFlightRepository {
	private Meta meta;
	@JsonProperty("data")
    private List<FlightOffer> FlightOffers;
	@JsonProperty("dictionaries")
	private Map<String, Location> dictionaries;
}

@Getter
class Meta {
    private int count;
    private Links links;
}

@Getter
class Links {
    private String self;
}

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
class FlightOffer {
    private String type;
    private int id;
    private String source;
    private boolean instantTicketingRequired;
    private boolean nonHomogeneous;
    private boolean oneWay;
    private String lastTicketingDate;
    private String lastTicketingDateTime;
    private int numberOfBookableSeats;
    private List<Itinerary> itineraries;
    private Price price;
    private PricingOptions pricingOptions;
    private List<String> validatingAirlineCodes;
    private List<TravelerPricing> travelerPricings;
}

@Getter
class Itinerary {
    private String duration;
    private List<Segment> segments;
}

@Getter
class Segment {
	private Departure departure;
    private Arrival arrival;
    private String carrierCode;
    private String number;
    private Aircraft aircraft;
    private Operating operating;
    private String duration;
    private int id;
    private int numberOfStops;
    private boolean blacklistedInEU;
}

@Getter
class Departure {
    private String iataCode;
    private String terminal;
    private String at;
}

@Getter
class Arrival {
    private String iataCode;
    private String terminal;
    private String at;
}

@Getter
class Aircraft {
    private String code;
}

@Getter
class Operating {
    private String carrierCode;
}

@Getter
class Price {
    private String currency;
    private double total;
    private double base;
    private List<Fee> fees;
    private double grandTotal;
}

@Getter
class Fee {
    private double amount;
    private String type;
}

@Getter
class PricingOptions {
    private List<String> fareType;
    private boolean includedCheckedBagsOnly;
}

@Getter
class TravelerPricing {
    private int travelerId;
    private String fareOption;
    private String travelerType;
    private Price price;
    private List<FareDetailsBySegment> fareDetailsBySegment;
}

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
class FareDetailsBySegment {
    private int segmentId;
    private String cabin;
    private String fareBasis;
    private String classType;
    private IncludedCheckedBags includedCheckedBags;
    private IncludedCabinBags includedCabinBags;
}

@Getter
class IncludedCheckedBags {
    private int quantity;
    private double weight;
    private String weightUnit;
}

@Getter
class IncludedCabinBags {
    private int quantity;
    private double weight;
    private String weightUnit;
}

@Data
class Location {
    private String cityCode;
    private String countryCode;
    
    @JsonAnySetter
    private Map<String, Object> additionalProperties = new HashMap<>();
}
