package com.restaurant.picker.restaurantservice.mapper;

import com.google.maps.places.v1.Review;
import com.restaurant.picker.restaurantservice.dto.ReviewDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;


@Component
@RequiredArgsConstructor
public class ReviewDTOMapper {
    private final AuthorDTOMapper authorDTOMapper;

    public ReviewDTO toReviewDTO(Review review) {
        if (review == null) return null;
        return ReviewDTO.builder()
                .name(review.getName())
                .relativePublishTimeDescription(review.getRelativePublishTimeDescription())
                .rating(review.getRating())
                .text(review.getText().getText())
                .authorAttribution(authorDTOMapper.toAuthorDTO(review.getAuthorAttribution()))
                .publishTime(Instant.ofEpochSecond(review.getPublishTime().getSeconds(), review.getPublishTime().getNanos()))
                .flagContentUri(review.getFlagContentUri())
                .googleMapsUri(review.getGoogleMapsUri())
                .build();
    }
}
