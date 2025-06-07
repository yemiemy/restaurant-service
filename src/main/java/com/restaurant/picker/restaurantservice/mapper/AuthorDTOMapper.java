package com.restaurant.picker.restaurantservice.mapper;

import com.google.maps.places.v1.AuthorAttribution;
import com.restaurant.picker.restaurantservice.dto.AuthorDTO;
import org.springframework.stereotype.Component;

@Component
public class AuthorDTOMapper {

    public AuthorDTO toAuthorDTO(AuthorAttribution authorAttribution) {
        if (authorAttribution == null) return null;

        return AuthorDTO.builder()
                .displayName(authorAttribution.getDisplayName())
                .uri(authorAttribution.getUri())
                .photoUri(authorAttribution.getPhotoUri())
                .build();
    }
}
