package com.restaurant.picker.restaurantservice.mapper;

import com.google.maps.places.v1.Place;
import com.restaurant.picker.restaurantservice.dto.PaymentOptionsDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentOptionsDTOMapper {

    PaymentOptionsDTO toPaymentOptionsDTO(Place.PaymentOptions paymentOptions);
}
