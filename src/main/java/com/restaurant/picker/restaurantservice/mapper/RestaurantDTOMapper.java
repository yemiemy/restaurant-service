package com.restaurant.picker.restaurantservice.mapper;

import com.google.maps.places.v1.Place;
import com.restaurant.picker.restaurantservice.dto.RestaurantDTO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = { ReviewDTOMapper.class, PaymentOptionsDTOMapper.class, OpeningHourDTOMapper.class }
)
public interface RestaurantDTOMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "displayName", source = "displayName.text")
    @Mapping(target = "types", source = "typesList")
    @Mapping(target = "primaryType", source = "primaryType")
    @Mapping(target = "primaryTypeDisplayName", source = "primaryTypeDisplayName.text")
    @Mapping(target = "nationalPhoneNumber", source = "nationalPhoneNumber")
    @Mapping(target = "internationalPhoneNumber", source = "internationalPhoneNumber")
    @Mapping(target = "formattedAddress", source = "formattedAddress")
    @Mapping(target = "shortFormattedAddress", source = "shortFormattedAddress")
    @Mapping(target = "googleMapsUri", source = "googleMapsUri")
    @Mapping(target = "websiteUri", source = "websiteUri")
    @Mapping(target = "rating", source = "rating")
    @Mapping(target = "reviews", source = "reviewsList")
    @Mapping(target = "userRatingCount", source = "userRatingCount")
    @Mapping(target = "businessStatus", expression = "java(place.getBusinessStatus().name())")
    @Mapping(target = "priceLevel", expression = "java(place.getPriceLevel().name())")
    @Mapping(target = "openingHours", source = "regularOpeningHours")
    @Mapping(target = "paymentOptions", source = "paymentOptions")
        // MapStruct will automatically map the rest if property names/types match
    RestaurantDTO toRestaurantDTO(Place place);

    List<RestaurantDTO> toRestaurantDTOs(List<Place> places);
}

//
//@Component
//@RequiredArgsConstructor
//public class RestaurantDTOMapper {
//
//    private final ReviewDTOMapper reviewDTOMapper;
//    private final PaymentOptionsDTOMapper paymentOptionsDTOMapper;
//    private final OpeningHourDTOMapper openingHourDTOMapper;
//
//    public RestaurantDTO toRestaurantDTO(Place place) {
//        if (place == null) return null;
//
//        List<ReviewDTO> reviews = mapReviews(place.getReviewsList());
//        OpeningHourDTO openingHourDTO = openingHourDTOMapper.toOpeningHourDTO(place.getRegularOpeningHours());
//        PaymentOptionsDTO paymentOptionsDTO = paymentOptionsDTOMapper.toPaymentOptionsDTO(place.getPaymentOptions());
//
//        return RestaurantDTO.builder()
//                .id(place.getId())
//                .displayName(place.getDisplayName().getText())
//                .types(place.getTypesList())
//                .primaryType(place.getPrimaryType())
//                .primaryTypeDisplayName(place.getPrimaryTypeDisplayName().getText())
//                .nationalPhoneNumber(place.getNationalPhoneNumber())
//                .internationalPhoneNumber(place.getInternationalPhoneNumber())
//                .formattedAddress(place.getFormattedAddress())
//                .shortFormattedAddress(place.getShortFormattedAddress())
//                .googleMapsUri(place.getGoogleMapsUri())
//                .websiteUri(place.getWebsiteUri())
//                .rating(place.getRating())
//                .reviews(reviews)
//                .userRatingCount(place.getUserRatingCount())
//                .businessStatus(place.getBusinessStatus().name())
//                .priceLevel(place.getPriceLevel().name())
//                .openingHours(openingHourDTO)
//                .takeout(place.getTakeout())
//                .delivery(place.getDelivery())
//                .dineIn(place.getDineIn())
//                .curbsidePickup(place.getCurbsidePickup())
//                .reservable(place.getReservable())
//                .servesLunch(place.getServesLunch())
//                .servesDinner(place.getServesDinner())
//                .servesBrunch(place.getServesBrunch())
//                .servesVegetarianFood(place.getServesVegetarianFood())
//                .liveMusic(place.getLiveMusic())
//                .servesDessert(place.getServesDessert())
//                .servesCoffee(place.getServesCoffee())
//                .goodForChildren(place.getGoodForChildren())
//                .restroom(place.getRestroom())
//                .goodForGroups(place.getGoodForGroups())
//                .goodForWatchingSports(place.getGoodForWatchingSports())
//                .paymentOptions(paymentOptionsDTO)
//                .build();
//
//    }
//
//    private List<ReviewDTO> mapReviews(List<Review> reviews) {
//        if (reviews == null) return Collections.emptyList();
//        return reviews.stream().map(reviewDTOMapper::toReviewDTO).collect(Collectors.toList());
//    }
//}
