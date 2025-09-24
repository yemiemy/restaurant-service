package com.restaurant.picker.restaurantservice.mapper;

import com.google.maps.places.v1.Place;
import com.google.protobuf.LazyStringArrayList;
import com.google.protobuf.ProtocolStringList;
import com.google.protobuf.Timestamp;
import com.restaurant.picker.restaurantservice.dto.OpeningHourDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class OpeningHourDTOMapperTest {

    @Autowired
    private OpeningHourDTOMapper mapper;

    @Test
    public void testMapOpeningHoursCorrectly() {
        // arrange
        Place.OpeningHours openingHours = mock(Place.OpeningHours.class);
        when(openingHours.getOpenNow()).thenReturn(false);
        ProtocolStringList times = new LazyStringArrayList();
        times.add("Mon 12:00 - 06:00");
        when(openingHours.getWeekdayDescriptionsList()).thenReturn(times);

        // act
        OpeningHourDTO openingHourDTO = mapper.toOpeningHourDTO(openingHours);

        // assert
        assertThat(openingHourDTO).isNotNull();
        assertThat(openingHourDTO.isOpenNow()).isFalse();
        assertThat(openingHourDTO.getWeekdayDescriptions()).containsExactly("Mon 12:00 - 06:00");
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