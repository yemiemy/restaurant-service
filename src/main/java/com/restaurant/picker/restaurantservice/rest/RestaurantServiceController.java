package com.restaurant.picker.restaurantservice.rest;

import com.restaurant.picker.restaurantservice.dto.RestaurantDTO;
import com.restaurant.picker.restaurantservice.model.SearchRestaurantRequest;
import com.restaurant.picker.restaurantservice.service.MapsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api")
public class RestaurantServiceController {

    private final MapsService mapsService;

    @PostMapping(path = "/restaurants")
    public ResponseEntity<List<RestaurantDTO>> searchRestaurants(@Valid @RequestBody SearchRestaurantRequest searchRestaurantRequest) {
        return ResponseEntity.ok(mapsService.searchRestaurants(searchRestaurantRequest));
    }

    @GetMapping(path = "/restaurants/{id}")
    public ResponseEntity<RestaurantDTO> fetchRestaurant(@Valid @PathVariable String id) {
        RestaurantDTO restaurant = mapsService.fetchRestaurant(id);
        if  (restaurant == null) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(mapsService.fetchRestaurant(id));
    }
}
