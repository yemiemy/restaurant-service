package com.restaurant.picker.restaurantservice.service;

import com.google.api.gax.rpc.FixedHeaderProvider;
import com.google.api.gax.rpc.HeaderProvider;
import com.google.maps.places.v1.*;
import com.google.type.LatLng;
import com.restaurant.picker.restaurantservice.config.GoogleApiConfig;
import com.restaurant.picker.restaurantservice.dto.RestaurantDTO;
import com.restaurant.picker.restaurantservice.mapper.RestaurantDTOMapper;
import com.restaurant.picker.restaurantservice.model.SearchRestaurantRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.google.api.gax.core.NoCredentialsProvider;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class MapsService {
    private final GoogleApiConfig googleApiConfig;

    private final RestaurantDTOMapper restaurantDTOMapper;

    public List<RestaurantDTO> textSearch(SearchRestaurantRequest searchRestaurantRequest) {
//        List<PriceLevel> desiredPriceLevels = Arrays.asList(
//                PriceLevel.valueOf("PRICE_LEVEL_MODERATE"),
//                PriceLevel.PRICE_LEVEL_EXPENSIVE
//        ); // AIzaSyDhuY4_4HmCfzTPm83I_cu2Rx892ctspCc
        log.info("Search restaurant request: {}", searchRestaurantRequest);
        List<PriceLevel> desiredPriceLevels = searchRestaurantRequest.getDesiredPriceLevels() != null ? searchRestaurantRequest.getDesiredPriceLevels().stream()
                .map(PriceLevel::valueOf)
                .toList()
                : Collections.emptyList();
        // Set up the Field Mask headers
        Map<String, String> headers = new HashMap<>();
        String fieldMaskString = "*";
        headers.put("x-goog-fieldmask", fieldMaskString);
        headers.put("x-goog-api-key", googleApiConfig.getKey());
        HeaderProvider headerProvider = FixedHeaderProvider.create(headers);
        try {
            // Build the settings object for the client
            PlacesSettings placesSettings = PlacesSettings.newBuilder()
                    .setHeaderProvider(headerProvider)
                    .setCredentialsProvider(NoCredentialsProvider.create())
                    .build();
            // Create the client using the settings.
            PlacesClient placesClient = PlacesClient.create(placesSettings);

            SearchTextRequest.Builder requestBuilder = SearchTextRequest.newBuilder()
                    .setTextQuery(searchRestaurantRequest.getSearchQuery())
                    .setMaxResultCount(5);

            // Create the location bias using a circle
            SearchTextRequest.LocationBias locationBias = createLocationBias(searchRestaurantRequest);
            if (locationBias != null) {
                requestBuilder.setLocationBias(locationBias);
            }
            if (searchRestaurantRequest.getMinPlaceRating() != null) {
                requestBuilder.setMinRating(searchRestaurantRequest.getMinPlaceRating());
            }
            if (searchRestaurantRequest.getDesiredPriceLevels() != null) {
                requestBuilder.addAllPriceLevels(desiredPriceLevels);
            }
            if (searchRestaurantRequest.getRequireOpenNow() != null) {
                requestBuilder.setOpenNow(searchRestaurantRequest.getRequireOpenNow());
            }
            if (searchRestaurantRequest.getRankPreference() != null) {
                requestBuilder.setRankPreference(SearchTextRequest.RankPreference.valueOf(searchRestaurantRequest.getRankPreference()));
            } else {
               requestBuilder.setRankPreference(SearchTextRequest.RankPreference.RELEVANCE);
            }

            // Build the Text Search request
            SearchTextRequest request = requestBuilder.build();
            // Call Text Search and output the response to the console
            SearchTextResponse response = placesClient.searchText(request);
            List<RestaurantDTO> restaurantDTOList = response.getPlacesList().stream()
                    .map(restaurantDTOMapper::toRestaurantDTO)
                    .toList();

            placesClient.close();
            return restaurantDTOList;
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            log.error("An error occurred: {}", e.getMessage());
        }
        return null;
    }

    private SearchTextRequest.LocationBias createLocationBias(SearchRestaurantRequest searchRestaurantRequest) {
        if (searchRestaurantRequest.getLatitude() == null || searchRestaurantRequest.getLongitude() == null) {
            return null;
        }
        LatLng centerPoint = LatLng.newBuilder()
                .setLatitude(searchRestaurantRequest.getLatitude())
                .setLongitude(searchRestaurantRequest.getLongitude())
                .build();
        Circle circleArea = Circle.newBuilder()
                .setCenter(centerPoint)
                .setRadius(searchRestaurantRequest.getRadiusMeters())
                .build();
        // Create the location bias using a circle
        return SearchTextRequest.LocationBias.newBuilder()
                .setCircle(circleArea)
                .build();
    }
}
