package com.restaurant.picker.restaurantservice.mapper;

import com.google.maps.places.v1.Place;
import com.restaurant.picker.restaurantservice.dto.OpeningHourDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OpeningHourDTOMapper {

    @Mapping(target = "openNow", source = "openNow")
    @Mapping(target = "weekdayDescriptions", source = "weekdayDescriptionsList")
    OpeningHourDTO toOpeningHourDTO(Place.OpeningHours openingHours);
}