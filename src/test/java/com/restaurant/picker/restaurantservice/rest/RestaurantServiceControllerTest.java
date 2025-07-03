package com.restaurant.picker.restaurantservice.rest;

import com.restaurant.picker.restaurantservice.dto.RestaurantDTO;
import com.restaurant.picker.restaurantservice.model.SearchRestaurantRequest;
import com.restaurant.picker.restaurantservice.service.MapsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class RestaurantServiceControllerTest {

    @MockitoBean
    private MapsService mapsService;

    @Autowired
    private RestaurantServiceController restaurantServiceController;

    @Test
    public void testSearchRestaurants_Success() {
        // arrange
        SearchRestaurantRequest searchRestaurantRequest = SearchRestaurantRequest.builder()
                .searchQuery("pizza restaurant")
                .build();
        RestaurantDTO restaurantDTO = RestaurantDTO.builder().displayName("Some Pizza Shop").build();
        when(mapsService.searchRestaurants(searchRestaurantRequest)).thenReturn(List.of(restaurantDTO));

        // act
        List<RestaurantDTO> response = restaurantServiceController.searchRestaurants(searchRestaurantRequest).getBody();

        // assert
        assertThat(response).hasSize(1);
        assertThat(response).extracting(RestaurantDTO::getDisplayName).containsExactly("Some Pizza Shop");
    }

    @Test
    public void testFetchRestaurant_Success() {
        // arrange
        String restaurantId = "place123";
        RestaurantDTO restaurantDTO = RestaurantDTO.builder().id(restaurantId).displayName("Some Pizza Shop").build();
        when(mapsService.fetchRestaurant(restaurantId)).thenReturn(restaurantDTO);

        // act
        RestaurantDTO response = restaurantServiceController.fetchRestaurant(restaurantId).getBody();

        // assert
        assertThat(response).isNotNull();
        assertThat(response.getDisplayName()).isEqualTo(restaurantDTO.getDisplayName());
    }

    @Test
    public void testFetchRestaurant_BadRequest() {
        // arrange
        String restaurantId = "place123";
        when(mapsService.fetchRestaurant(restaurantId)).thenReturn(null);

        // act
        ResponseEntity<RestaurantDTO> response = restaurantServiceController.fetchRestaurant(restaurantId);

        // assert
        assertThat(response).isNotNull();
        assertThat(response.getBody()).isNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}