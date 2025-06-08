package com.restaurant.picker.restaurantservice.mapper;

import com.google.maps.places.v1.Place;
import com.restaurant.picker.restaurantservice.dto.OpeningHourDTO;
import org.springframework.stereotype.Component;

@Component
public class OpeningHourDTOMapper {

    public OpeningHourDTO toOpeningHourDTO(Place.OpeningHours openingHours) {
        if (openingHours == null) return null;

        return OpeningHourDTO.builder()
                .openNow(openingHours.getOpenNow())
                .weekdayDescriptions(openingHours.getWeekdayDescriptionsList())
                .build();
    }
}
