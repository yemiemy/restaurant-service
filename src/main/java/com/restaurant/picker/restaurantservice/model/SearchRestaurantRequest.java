package com.restaurant.picker.restaurantservice.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
public class SearchRestaurantRequest {

    @NotBlank(message = "searchQuery is required")
    private String searchQuery;
    private Double latitude;
    private Double longitude;
    @Builder.Default
    private Double radiusMeters = 100.0;
    @Builder.Default
    private Double minPlaceRating = 3.0;
    @Builder.Default
    private List<PriceLevel> desiredPriceLevels = new ArrayList<>();
    @Builder.Default
    private Boolean requireOpenNow = false;
    @Builder.Default
    private RankPreference rankPreference = RankPreference.RELEVANCE;
}
