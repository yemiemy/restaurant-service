package com.restaurant.picker.restaurantservice.mapper;

import com.google.maps.places.v1.Place;
import com.restaurant.picker.restaurantservice.dto.PaymentOptionsDTO;
import org.springframework.stereotype.Component;

@Component
public class PaymentOptionsDTOMapper {

    public PaymentOptionsDTO toPaymentOptionsDTO(Place.PaymentOptions paymentOptions) {
        if (paymentOptions == null) return null;
        return PaymentOptionsDTO.builder()
                .acceptsCreditCards(paymentOptions.getAcceptsCreditCards())
                .acceptsCashOnly(paymentOptions.getAcceptsCashOnly())
                .build();
    }
}
