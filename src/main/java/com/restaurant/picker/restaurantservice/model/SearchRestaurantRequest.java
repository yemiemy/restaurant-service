package com.restaurant.picker.restaurantservice.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Builder
@Data
public class SearchRestaurantRequest {
    @NonNull
    private String searchQuery;
    private Double latitude;
    private Double longitude;
    private Double radiusMeters;
    private Double minPlaceRating;
    private List<String> desiredPriceLevels;
    private Boolean requireOpenNow;
    private String rankPreference;
}
