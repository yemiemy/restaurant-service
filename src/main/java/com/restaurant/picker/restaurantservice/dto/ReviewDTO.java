package com.restaurant.picker.restaurantservice.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;


@Builder
@Data
public class ReviewDTO {
    private String name;
    private String relativePublishTimeDescription;
    private double rating;
    private String text;
    private AuthorDTO authorAttribution;
    private Instant publishTime;
    private String flagContentUri;
    private String googleMapsUri;
}
