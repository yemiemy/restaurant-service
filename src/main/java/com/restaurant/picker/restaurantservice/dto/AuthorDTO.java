package com.restaurant.picker.restaurantservice.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthorDTO {
    private String displayName;
    private String uri;
    private String photoUri;
}
