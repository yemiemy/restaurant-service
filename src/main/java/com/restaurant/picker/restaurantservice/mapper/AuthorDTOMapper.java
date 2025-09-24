package com.restaurant.picker.restaurantservice.mapper;

import com.google.maps.places.v1.AuthorAttribution;
import com.restaurant.picker.restaurantservice.dto.AuthorDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorDTOMapper {
    AuthorDTO toAuthorDTO(AuthorAttribution authorAttribution);
}
