package com.restaurant.picker.restaurantservice.rest;

import com.restaurant.picker.restaurantservice.dto.RestaurantDTO;
import com.restaurant.picker.restaurantservice.model.SearchRestaurantRequest;
import com.restaurant.picker.restaurantservice.service.MapsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class RestaurantServiceController {

    private final MapsService mapsService;

    @GetMapping
    public String helloWorld() {
        return "Hello World!";
    }

    @GetMapping(path = "/restaurants")
    public ResponseEntity<List<RestaurantDTO>> getRestaurants(@ModelAttribute SearchRestaurantRequest searchRestaurantRequest) {
        return ResponseEntity.ok(mapsService.textSearch(searchRestaurantRequest));
    }

    // Custom handler for missing required request parameters
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<String> handleMissingParams(MissingServletRequestParameterException ex) {
        String name = ex.getParameterName();
        String message = "Required query parameter '" + name + "' is missing";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }
}
