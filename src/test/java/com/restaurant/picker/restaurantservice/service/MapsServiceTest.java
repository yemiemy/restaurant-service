package com.restaurant.picker.restaurantservice.service;

import com.google.maps.places.v1.*;
import com.google.type.LocalizedText;
import com.restaurant.picker.restaurantservice.dto.RestaurantDTO;
import com.restaurant.picker.restaurantservice.factory.PlacesClientFactory;
import com.restaurant.picker.restaurantservice.model.SearchRestaurantRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@SpringBootTest
class MapsServiceTest {

    @MockitoBean
    private PlacesClientFactory placesClientFactory;
    @Mock
    private PlacesClient placesClient;
    @Mock
    private SearchTextResponse searchTextResponse;

    @Autowired
    private MapsService mapsService;

    @Test
    public void shouldReturnListOfRestaurantsForValidSearchRequest() throws IOException {
        // arrange
        SearchRestaurantRequest searchRestaurantRequest = SearchRestaurantRequest.builder()
                .searchQuery("pizza restaurant")
                .build();
        Place expectedPlace = Place.newBuilder()
                .setId("place123")
                .setDisplayName(LocalizedText.newBuilder().setText("Some Pizza Shop").build())
                .addTypes("restaurant")
                .setPrimaryType("food")
                .setPrimaryTypeDisplayName(LocalizedText.newBuilder().setText("Food Place").build())
                .setNationalPhoneNumber("+1 555-1234")
                .setInternationalPhoneNumber("+1-555-1234")
                .setFormattedAddress("123 Main St")
                .setShortFormattedAddress("Main St")
                .setGoogleMapsUri("http://maps.google.com/test")
                .setWebsiteUri("http://restaurant.com")
                .setRating(4.5)
                .addReviews(Review.newBuilder().build())
                .setUserRatingCount(200)
                .setBusinessStatus(Place.BusinessStatus.OPERATIONAL)
                .setPriceLevel(PriceLevel.PRICE_LEVEL_MODERATE)
                .setRegularOpeningHours(Place.OpeningHours.newBuilder().build())
                .setTakeout(true)
                .setDelivery(true)
                .setDineIn(true)
                .setCurbsidePickup(false)
                .setReservable(true)
                .setServesLunch(true)
                .setServesDinner(true)
                .setServesBrunch(false)
                .setServesVegetarianFood(true)
                .setLiveMusic(false)
                .setServesDessert(true)
                .setServesCoffee(true)
                .setGoodForChildren(true)
                .setRestroom(true)
                .setGoodForGroups(true)
                .setGoodForWatchingSports(false)
                .setPaymentOptions(Place.PaymentOptions.newBuilder().build())
                .build();
        when(searchTextResponse.getPlacesList()).thenReturn(List.of(expectedPlace));
        when(placesClient.searchText(any())).thenReturn(searchTextResponse);
        when(placesClientFactory.createClient("places.")).thenReturn(placesClient);

        // act
        List<RestaurantDTO> result = mapsService.searchRestaurants(searchRestaurantRequest);

        // assert
        assertThat(result).hasSize(1);
        assertThat(result).extracting(RestaurantDTO::getDisplayName).containsExactly("Some Pizza Shop");
        assertThat(result).extracting(RestaurantDTO::getId).containsExactly("place123");
    }

    @Test
    public void shouldReturnRestaurantForValidId() throws IOException {
        // arrange
        Place expectedPlace = Place.newBuilder()
                .setId("place123")
                .setDisplayName(LocalizedText.newBuilder().setText("Some Pizza Shop").build())
                .addTypes("restaurant")
                .setPrimaryType("food")
                .setPrimaryTypeDisplayName(LocalizedText.newBuilder().setText("Food Place").build())
                .setNationalPhoneNumber("+1 555-1234")
                .setInternationalPhoneNumber("+1-555-1234")
                .setFormattedAddress("123 Main St")
                .setShortFormattedAddress("Main St")
                .setGoogleMapsUri("http://maps.google.com/test")
                .setWebsiteUri("http://restaurant.com")
                .setRating(4.5)
                .addReviews(Review.newBuilder().build())
                .setUserRatingCount(200)
                .setBusinessStatus(Place.BusinessStatus.OPERATIONAL)
                .setPriceLevel(PriceLevel.PRICE_LEVEL_MODERATE)
                .setRegularOpeningHours(Place.OpeningHours.newBuilder().build())
                .setTakeout(true)
                .setDelivery(true)
                .setDineIn(true)
                .setCurbsidePickup(false)
                .setReservable(true)
                .setServesLunch(true)
                .setServesDinner(true)
                .setServesBrunch(false)
                .setServesVegetarianFood(true)
                .setLiveMusic(false)
                .setServesDessert(true)
                .setServesCoffee(true)
                .setGoodForChildren(true)
                .setRestroom(true)
                .setGoodForGroups(true)
                .setGoodForWatchingSports(false)
                .setPaymentOptions(Place.PaymentOptions.newBuilder().build())
                .build();
        when(placesClient.getPlace((GetPlaceRequest) any())).thenReturn(expectedPlace);
        when(placesClientFactory.createClient("")).thenReturn(placesClient);

        // act
        RestaurantDTO result = mapsService.fetchRestaurant("place123");

        // assert
        assertThat(result).isNotNull();
        assertThat(result.getDisplayName()).isEqualTo("Some Pizza Shop");
        assertThat(result.getId()).isEqualTo("place123");
    }

    @Test
    public void shouldReturnNullRestaurantForInvalidId() throws IOException {
        // arrange
        when(placesClient.getPlace((GetPlaceRequest) any())).thenReturn(null);
        when(placesClientFactory.createClient("")).thenReturn(placesClient);

        // act
        RestaurantDTO result = mapsService.fetchRestaurant("place123");

        // assert
        assertThat(result).isNull();
    }
}