package com.restaurant.picker.restaurantservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Builder
@Data
public class RestaurantDTO {
    private String id;
    private String displayName;
    @Builder.Default
    private List<String> types = Collections.emptyList();
    private String primaryType;
    private String primaryTypeDisplayName;
    private String nationalPhoneNumber;
    private String internationalPhoneNumber;
    private String formattedAddress;
    private String shortFormattedAddress;
    private String googleMapsUri;
    private String websiteUri;
    private double rating;
    @Builder.Default
    private List<ReviewDTO> reviews = Collections.emptyList();
    private int userRatingCount;
    private String businessStatus; // OPERATIONAL
    private String priceLevel; // PRICE_LEVEL_MODERATE, PRICE_LEVEL_EXPENSIVE
    private OpeningHourDTO openingHours;
    private String adrFormatAddress;

    private boolean takeout;
    private boolean delivery;
    private boolean dineIn;
    private boolean curbsidePickup;
    private boolean reservable;
    private boolean servesLunch;
    private boolean servesDinner;
    private boolean servesBrunch;
    private boolean servesVegetarianFood;
    private boolean liveMusic;
    private boolean servesDessert;
    private boolean servesCoffee;
    private boolean goodForChildren;
    private boolean restroom;
    private boolean goodForGroups;
    private boolean goodForWatchingSports;

    private PaymentOptionsDTO paymentOptions;
}
