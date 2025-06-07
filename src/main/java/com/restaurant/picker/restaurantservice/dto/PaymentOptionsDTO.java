package com.restaurant.picker.restaurantservice.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PaymentOptionsDTO {
    private boolean acceptsCreditCards;
    private boolean acceptsCashOnly;
}
