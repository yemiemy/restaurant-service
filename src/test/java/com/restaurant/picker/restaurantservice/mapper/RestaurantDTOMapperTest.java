package com.restaurant.picker.restaurantservice.mapper;

import com.google.maps.places.v1.Place;
import com.google.maps.places.v1.PriceLevel;
import com.google.maps.places.v1.Review;
import com.google.type.LocalizedText;
import com.restaurant.picker.restaurantservice.dto.OpeningHourDTO;
import com.restaurant.picker.restaurantservice.dto.PaymentOptionsDTO;
import com.restaurant.picker.restaurantservice.dto.RestaurantDTO;
import com.restaurant.picker.restaurantservice.dto.ReviewDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class RestaurantDTOMapperTest {
    @MockitoBean
    private ReviewDTOMapper reviewDTOMapper;

    @MockitoBean
    private PaymentOptionsDTOMapper paymentOptionsDTOMapper;

    @MockitoBean
    private OpeningHourDTOMapper openingHourDTOMapper;

    private RestaurantDTOMapper restaurantDTOMapper;

    @BeforeEach
    void setUp() {
        restaurantDTOMapper = new RestaurantDTOMapper(reviewDTOMapper, paymentOptionsDTOMapper, openingHourDTOMapper);
    }

    @Test
    public void testToRestaurantDTO() {
        // arrange
        Place place = Place.newBuilder()
                .setId("place123")
                .setDisplayName(LocalizedText.newBuilder().setText("Test Restaurant").build())
                .addTypes("restaurant")
                .setPrimaryType("food")
                .setPrimaryTypeDisplayName(LocalizedText.newBuilder().setText("Food Place").build())
                .setNationalPhoneNumber("+1 555-1234")
                .setInternationalPhoneNumber("+1-555-1234")
                .setFormattedAddress("123 Main St")
                .setShortFormattedAddress("Main St")
                .setGoogleMapsUri("http://maps.google.com/test")
                .setWebsiteUri("http://restaurant.com")
                .setRating(4.5)
                .addReviews(Review.newBuilder().build())
                .setUserRatingCount(200)
                .setBusinessStatus(Place.BusinessStatus.OPERATIONAL)
                .setPriceLevel(PriceLevel.PRICE_LEVEL_MODERATE)
                .setRegularOpeningHours(Place.OpeningHours.newBuilder().build())
                .setTakeout(true)
                .setDelivery(true)
                .setDineIn(true)
                .setCurbsidePickup(false)
                .setReservable(true)
                .setServesLunch(true)
                .setServesDinner(true)
                .setServesBrunch(false)
                .setServesVegetarianFood(true)
                .setLiveMusic(false)
                .setServesDessert(true)
                .setServesCoffee(true)
                .setGoodForChildren(true)
                .setRestroom(true)
                .setGoodForGroups(true)
                .setGoodForWatchingSports(false)
                .setPaymentOptions(Place.PaymentOptions.newBuilder().build())
                .build();

        ReviewDTO reviewDTO = ReviewDTO.builder().build();
        when(reviewDTOMapper.toReviewDTO(any())).thenReturn(reviewDTO);

        OpeningHourDTO openingHourDTO = OpeningHourDTO.builder().build();
        when(openingHourDTOMapper.toOpeningHourDTO(any())).thenReturn(openingHourDTO);

        PaymentOptionsDTO paymentOptionsDTO = PaymentOptionsDTO.builder().build();
        when(paymentOptionsDTOMapper.toPaymentOptionsDTO(any())).thenReturn(paymentOptionsDTO);

        // act
        RestaurantDTO dto = restaurantDTOMapper.toRestaurantDTO(place);

        // assert
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(place.getId());
        assertThat(dto.getDisplayName()).isEqualTo(place.getDisplayName().getText());
        assertThat(dto.getPrimaryType()).isEqualTo(place.getPrimaryType());
        assertThat(dto.getPrimaryTypeDisplayName()).isEqualTo(place.getPrimaryTypeDisplayName().getText());
        assertThat(dto.getWebsiteUri()).isEqualTo(place.getWebsiteUri());
        assertThat(dto.getBusinessStatus()).isEqualTo(place.getBusinessStatus().name());
        assertThat(dto.getPriceLevel()).isEqualTo(place.getPriceLevel().name());
        assertThat(dto.isTakeout()).isTrue();
        assertThat(dto.getReviews()).hasSize(1);

        // verify mocks were called
        verify(reviewDTOMapper, times(1)).toReviewDTO(any());
        verify(openingHourDTOMapper, times(1)).toOpeningHourDTO(any());
        verify(paymentOptionsDTOMapper, times(1)).toPaymentOptionsDTO(any());
    }

    @Test
    public void shouldReturnNullWhenPlaceIsNull() {
        // arrange
        Place place = null;

        // act
        RestaurantDTO dto = restaurantDTOMapper.toRestaurantDTO(place);

        // assert
        assertThat(dto).isNull();
    }
}