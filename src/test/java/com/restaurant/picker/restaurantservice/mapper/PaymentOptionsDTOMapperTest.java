package com.restaurant.picker.restaurantservice.mapper;

import com.google.maps.places.v1.Place;
import com.restaurant.picker.restaurantservice.dto.PaymentOptionsDTO;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PaymentOptionsDTOMapperTest {
    private final PaymentOptionsDTOMapper mapper = new PaymentOptionsDTOMapper();

    @Test
    public void testMapPaymentOptionsCorrectly(){
        // arrange
        Place.PaymentOptions paymentOptions = mock(Place.PaymentOptions.class);
        when(paymentOptions.getAcceptsCreditCards()).thenReturn(true);
        when(paymentOptions.getAcceptsCashOnly()).thenReturn(false);

        // act
        PaymentOptionsDTO  paymentOptionsDTO = mapper.toPaymentOptionsDTO(paymentOptions);

        // assert
        assertThat(paymentOptionsDTO.isAcceptsCreditCards()).isTrue();
        assertThat(paymentOptionsDTO.isAcceptsCashOnly()).isFalse();
    }

    @Test
    public void shouldReturnNullWhenPaymentOptionsIsNull(){
        // arrange
        Place.PaymentOptions paymentOptions = null;

        // act
        PaymentOptionsDTO  paymentOptionsDTO = mapper.toPaymentOptionsDTO(paymentOptions);

        // assert
        assertThat(paymentOptionsDTO).isNull();
    }
}