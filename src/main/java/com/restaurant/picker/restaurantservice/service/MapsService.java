package com.restaurant.picker.restaurantservice.service;

import com.google.maps.places.v1.*;
import com.google.type.LatLng;
import com.restaurant.picker.restaurantservice.dto.RestaurantDTO;
import com.restaurant.picker.restaurantservice.factory.PlacesClientFactory;
import com.restaurant.picker.restaurantservice.mapper.RestaurantDTOMapper;
import com.restaurant.picker.restaurantservice.model.SearchRestaurantRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class MapsService {
    private final PlacesClientFactory placesClientFactory;

    private final RestaurantDTOMapper restaurantDTOMapper;

    public List<RestaurantDTO> searchRestaurants(SearchRestaurantRequest searchRestaurantRequest) {
        log.info("Search restaurant request: {}", searchRestaurantRequest);
        List<PriceLevel> desiredPriceLevels = searchRestaurantRequest.getDesiredPriceLevels() != null ? searchRestaurantRequest.getDesiredPriceLevels().stream()
                .map(PriceLevel::valueOf)
                .toList()
                : Collections.emptyList();

        try(PlacesClient placesClient = placesClientFactory.createClient("places.")) {
            SearchTextRequest.Builder requestBuilder = SearchTextRequest.newBuilder()
//                    .setIncludedType("restaurant") TODO: doing this puts a hard restriction on the search space.
                    .setTextQuery(searchRestaurantRequest.getSearchQuery())
                    .setMaxResultCount(5);

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

            log.debug("Building search text request for: {}", searchRestaurantRequest);
            SearchTextRequest request = requestBuilder.build();
            SearchTextResponse response = placesClient.searchText(request);
            List<RestaurantDTO> restaurantDTOList = response.getPlacesList().stream()
                    .map(restaurantDTOMapper::toRestaurantDTO)
                    .toList();

            placesClient.close();
            log.info("{} restaurants fetched successfully.", restaurantDTOList.size());
            return restaurantDTOList;
        } catch (Exception e) {
            log.error("An error occurred: {}", e.getMessage());
        }
        return new ArrayList<>();
    }

    public RestaurantDTO fetchRestaurant(String placeId) {
        log.info("Fetch restaurant by Id: {}", placeId);
        String placeName = "places/" + placeId;
        try(PlacesClient placesClient = placesClientFactory.createClient("")) {
            GetPlaceRequest request = GetPlaceRequest.newBuilder()
                    .setName(placeName)
                    .build();
            Place place = placesClient.getPlace(request);
            placesClient.close();
            return restaurantDTOMapper.toRestaurantDTO(place);
        } catch (Exception e) {
            log.error("An error occurred: {}", e.getMessage(), e);
        }
        return null;
    }

    private SearchTextRequest.LocationBias createLocationBias(SearchRestaurantRequest searchRestaurantRequest) {
        if (searchRestaurantRequest.getLatitude() == null || searchRestaurantRequest.getLongitude() == null) {
            return null;
        }

        log.debug("Creating location bias for lat {} long {} provided", searchRestaurantRequest.getLatitude(), searchRestaurantRequest.getLongitude());
        LatLng centerPoint = LatLng.newBuilder()
                .setLatitude(searchRestaurantRequest.getLatitude())
                .setLongitude(searchRestaurantRequest.getLongitude())
                .build();
        Circle circleArea = Circle.newBuilder()
                .setCenter(centerPoint)
                .setRadius(searchRestaurantRequest.getRadiusMeters())
                .build();
        return SearchTextRequest.LocationBias.newBuilder()
                .setCircle(circleArea)
                .build();
    }
}
