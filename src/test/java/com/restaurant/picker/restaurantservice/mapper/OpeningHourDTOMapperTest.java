package com.restaurant.picker.restaurantservice.mapper;

import com.google.maps.places.v1.Place;
import com.google.protobuf.LazyStringArrayList;
import com.google.protobuf.ProtocolStringList;
import com.google.protobuf.Timestamp;
import com.restaurant.picker.restaurantservice.dto.OpeningHourDTO;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OpeningHourDTOMapperTest {

    private final OpeningHourDTOMapper mapper = new OpeningHourDTOMapper();

    @Test
    public void testMapOpeningHoursCorrectly() {
        // arrange
        Place.OpeningHours openingHours = mock(Place.OpeningHours.class);
        when(openingHours.getOpenNow()).thenReturn(false);
        ProtocolStringList times = new LazyStringArrayList();
        times.add("Mon 12:00 - 06:00");
        when(openingHours.getWeekdayDescriptionsList()).thenReturn(times);
        Timestamp timestamp = Timestamp.newBuilder().setSeconds(12).build();
        when(openingHours.getNextOpenTime()).thenReturn(timestamp);

        // act
        OpeningHourDTO openingHourDTO = mapper.toOpeningHourDTO(openingHours);

        // assert
        assertThat(openingHourDTO).isNotNull();
        assertThat(openingHourDTO.isOpenNow()).isFalse();
        assertThat(openingHourDTO.getWeekdayDescriptions()).containsExactly("Mon 12:00 - 06:00");
        assertThat(openingHourDTO.getNextOpenTime()).isEqualTo("1970-01-01T00:00:12Z");
    }

    @Test
    public void shouldReturnNullForNullOpeningHours() {
        // arrange
        Place.OpeningHours openingHours = null;

        // act
        OpeningHourDTO openingHourDTO = mapper.toOpeningHourDTO(openingHours);

        // assert
        assertThat(openingHourDTO).isNull();
    }
}