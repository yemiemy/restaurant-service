package com.restaurant.picker.restaurantservice.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Builder
@Data
public class OpeningHourDTO {
    private boolean openNow;
    private List<String> weekdayDescriptions;
    private Instant nextOpenTime;
}
