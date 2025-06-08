package com.restaurant.picker.restaurantservice.mapper;

import com.google.maps.places.v1.Place;
import com.google.maps.places.v1.Review;
import com.restaurant.picker.restaurantservice.dto.OpeningHourDTO;
import com.restaurant.picker.restaurantservice.dto.PaymentOptionsDTO;
import com.restaurant.picker.restaurantservice.dto.RestaurantDTO;
import com.restaurant.picker.restaurantservice.dto.ReviewDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RestaurantDTOMapper {

    private final ReviewDTOMapper reviewDTOMapper;
    private final PaymentOptionsDTOMapper paymentOptionsDTOMapper;
    private final OpeningHourDTOMapper openingHourDTOMapper;

    public RestaurantDTO toRestaurantDTO(Place place) {
        if (place == null) return null;

        List<ReviewDTO> reviews = mapReviews(place.getReviewsList());
        OpeningHourDTO openingHourDTO = openingHourDTOMapper.toOpeningHourDTO(place.getRegularOpeningHours());
        PaymentOptionsDTO paymentOptionsDTO = paymentOptionsDTOMapper.toPaymentOptionsDTO(place.getPaymentOptions());

        return RestaurantDTO.builder()
                .id(place.getId())
                .displayName(place.getDisplayName().getText())
                .types(place.getTypesList())
                .primaryType(place.getPrimaryType())
                .primaryTypeDisplayName(place.getPrimaryTypeDisplayName().getText())
                .nationalPhoneNumber(place.getNationalPhoneNumber())
                .internationalPhoneNumber(place.getInternationalPhoneNumber())
                .formattedAddress(place.getFormattedAddress())
                .shortFormattedAddress(place.getShortFormattedAddress())
                .googleMapsUri(place.getGoogleMapsUri())
                .websiteUri(place.getWebsiteUri())
                .rating(place.getRating())
                .reviews(reviews)
                .userRatingCount(place.getUserRatingCount())
                .businessStatus(place.getBusinessStatus().name())
                .priceLevel(place.getPriceLevel().name())
                .openingHours(openingHourDTO)
                .takeout(place.getTakeout())
                .delivery(place.getDelivery())
                .dineIn(place.getDineIn())
                .curbsidePickup(place.getCurbsidePickup())
                .reservable(place.getReservable())
                .servesLunch(place.getServesLunch())
                .servesDinner(place.getServesDinner())
                .servesBrunch(place.getServesBrunch())
                .servesVegetarianFood(place.getServesVegetarianFood())
                .liveMusic(place.getLiveMusic())
                .servesDessert(place.getServesDessert())
                .servesCoffee(place.getServesCoffee())
                .goodForChildren(place.getGoodForChildren())
                .restroom(place.getRestroom())
                .goodForGroups(place.getGoodForGroups())
                .goodForWatchingSports(place.getGoodForWatchingSports())
                .paymentOptions(paymentOptionsDTO)
                .build();

    }

    private List<ReviewDTO> mapReviews(List<Review> reviews) {
        if (reviews == null) return Collections.emptyList();
        return reviews.stream().map(reviewDTOMapper::toReviewDTO).collect(Collectors.toList());
    }
}
