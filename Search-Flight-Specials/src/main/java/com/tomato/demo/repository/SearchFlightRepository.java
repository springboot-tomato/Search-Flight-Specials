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
	public Meta meta;
	@JsonProperty("data")
	public List<FlightOffer> FlightOffers;
	@JsonProperty("dictionaries")
	public Map<String, Location> dictionaries;

	@Getter
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class FlightOffer {
		public String type;
		public int id;
		public String source;
		public boolean instantTicketingRequired;
		public boolean nonHomogeneous;
		public boolean oneWay;
		public String lastTicketingDate;
		public String lastTicketingDateTime;
		public int numberOfBookableSeats;
		public List<Itinerary> itineraries;
		public Price price;
		public PricingOptions pricingOptions;
		public List<String> validatingAirlineCodes;
		public List<TravelerPricing> travelerPricings;
	}

	@Getter
	public static class Meta {
		public int count;
		public Links links;
	}

	@Getter
	public static class Links {
		public String self;
	}

	@Getter
	public static class Itinerary {
		public String duration;
		public List<Segment> segments;
	}

	@Getter
	public static class Segment {
		public Departure departure;
		public Arrival arrival;
		public String carrierCode;
		public String number;
		public Aircraft aircraft;
		public Operating operating;
		public String duration;
		public int id;
		public int numberOfStops;
		public boolean blacklistedInEU;
	}

	@Getter
	public static class Departure {
		public String iataCode;
		public String terminal;
		public String at;
	}

	@Getter
	public static class Arrival {
		public String iataCode;
		public String terminal;
		public String at;
	}

	@Getter
	public static class Aircraft {
		public String code;
	}

	@Getter
	public static class Operating {
		public String carrierCode;
	}

	@Getter
	public static class Price {
		public String currency;
		public double total;
		public double base;
		public List<Fee> fees;
		public double grandTotal;
	}

	@Getter
	public static class Fee {
		public double amount;
		public String type;
	}

	@Getter
	public static class PricingOptions {
		public List<String> fareType;
		public boolean includedCheckedBagsOnly;
	}

	@Getter
	public static class TravelerPricing {
		public int travelerId;
		public String fareOption;
		public String travelerType;
		public Price price;
		public List<FareDetailsBySegment> fareDetailsBySegment;
	}

	@Getter
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class FareDetailsBySegment {
		public int segmentId;
		public String cabin;
		public String fareBasis;
		public String classType;
		public IncludedCheckedBags includedCheckedBags;
		public IncludedCabinBags includedCabinBags;
	}

	@Getter
	public static class IncludedCheckedBags {
		public int quantity;
		public double weight;
		public String weightUnit;
	}

	@Getter
	public static class IncludedCabinBags {
		public int quantity;
		public double weight;
		public String weightUnit;
	}

	@Data
	public static class Location {
		public String cityCode;
		public String countryCode;

		@JsonAnySetter
		public Map<String, Object> additionalProperties = new HashMap<>();
	}

}
