package com.restaurant.picker.restaurantservice.mapper;

import com.google.maps.places.v1.Review;
import com.restaurant.picker.restaurantservice.dto.ReviewDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;

@Mapper(componentModel = "spring", uses = { AuthorDTOMapper.class }, imports = { Instant.class })
public interface ReviewDTOMapper {

    @Mapping(target = "text", source = "text.text")
    @Mapping(target = "authorAttribution", source = "authorAttribution")
    @Mapping(
            target = "publishTime",
            expression = "java(Instant.ofEpochSecond(review.getPublishTime().getSeconds(), review.getPublishTime().getNanos()))"
    )
    ReviewDTO toReviewDTO(Review review);
}