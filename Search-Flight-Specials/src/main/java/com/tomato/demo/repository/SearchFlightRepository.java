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
	private List<FlightOffer> flightOffers;
	@JsonProperty("dictionaries")
	private Map<String, Dictionary> dictionaries;

	@Getter
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class FlightOffer {
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
	public static class Meta {
		private int count;
		private Links links;
	}

	@Getter
	public static class Links {
		private String self;
	}

	@Getter
	public static class Itinerary {
		private String duration;
		private List<Segment> segments;
	}

	@Getter
	public static class Segment {
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
	public static class Departure {
		private String iataCode;
		private String terminal;
		private String at;
	}

	@Getter
	public static class Arrival {
		private String iataCode;
		private String terminal;
		private String at;
	}

	@Getter
	public static class Aircraft {
		private String code;
	}

	@Getter
	public static class Operating {
		private String carrierCode;
	}

	@Getter
	public static class Price {
		private String currency;
		private double total;
		private double base;
		private List<Fee> fees;
		private double grandTotal;
	}

	@Getter
	public static class Fee {
		private double amount;
		private String type;
	}

	@Getter
	public static class PricingOptions {
		private List<String> fareType;
		private boolean includedCheckedBagsOnly;
	}

	@Getter
	public static class TravelerPricing {
		private int travelerId;
		private String fareOption;
		private String travelerType;
		private Price price;
		private List<FareDetailsBySegment> fareDetailsBySegment;
	}

	@Getter
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class FareDetailsBySegment {
		private int segmentId;
		private String cabin;
		private String fareBasis;
		private String classType;
		private IncludedCheckedBags includedCheckedBags;
		private IncludedCabinBags includedCabinBags;
	}

	@Getter
	public static class IncludedCheckedBags {
		private int quantity;
		private double weight;
		private String weightUnit;
	}

	@Getter
	public static class IncludedCabinBags {
		private int quantity;
		private double weight;
		private String weightUnit;
	}

	@Data
	public static class Dictionary {
		@JsonAnySetter
		private Map<String, Object> additionalProperties = new HashMap<>();
	}

}
