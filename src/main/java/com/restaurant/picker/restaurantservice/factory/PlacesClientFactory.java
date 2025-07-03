package com.restaurant.picker.restaurantservice.factory;

import com.google.api.gax.core.NoCredentialsProvider;
import com.google.api.gax.rpc.FixedHeaderProvider;
import com.google.api.gax.rpc.HeaderProvider;
import com.google.maps.places.v1.PlacesClient;
import com.google.maps.places.v1.PlacesSettings;
import com.restaurant.picker.restaurantservice.config.GoogleApiConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PlacesClientFactory {
    private final GoogleApiConfig googleApiConfig;

    public PlacesClient createClient(String fieldMaskSuffix) throws IOException {
        Map<String, String> headers = new HashMap<>();
        String fieldMaskString = buildFieldMaskString(fieldMaskSuffix);
        headers.put("x-goog-fieldmask", fieldMaskString);
        headers.put("x-goog-api-key", googleApiConfig.getKey());

        HeaderProvider headerProvider = FixedHeaderProvider.create(headers);

        PlacesSettings placesSettings = PlacesSettings.newBuilder()
                .setHeaderProvider(headerProvider)
                .setCredentialsProvider(NoCredentialsProvider.create())
                .build();

        return PlacesClient.create(placesSettings);
    }

    private String buildFieldMaskString(String suffix) {
        String[] fields = {
                "id", "displayName", "types", "primaryType", "primaryTypeDisplayName", "nationalPhoneNumber",
                "internationalPhoneNumber", "formattedAddress", "shortFormattedAddress", "googleMapsUri", "websiteUri",
                "rating", "reviews", "userRatingCount", "businessStatus", "priceLevel", "regularOpeningHours", "takeout",
                "delivery", "dineIn", "curbsidePickup", "reservable", "servesLunch", "servesDinner", "servesBrunch",
                "servesVegetarianFood", "liveMusic", "servesDessert", "servesCoffee", "goodForChildren", "restroom",
                "goodForGroups", "goodForWatchingSports", "paymentOptions"
        };
        return Arrays.stream(fields)
                .map(field -> suffix + field)
                .collect(Collectors.joining(","));
    }
}
