package com.restaurant.picker.restaurantservice.mapper;

import com.google.maps.places.v1.AuthorAttribution;
import com.google.maps.places.v1.Review;
import com.google.protobuf.Timestamp;
import com.google.type.LocalizedText;
import com.restaurant.picker.restaurantservice.dto.AuthorDTO;
import com.restaurant.picker.restaurantservice.dto.ReviewDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class ReviewDTOMapperTest {

    @Autowired
    private ReviewDTOMapper mapper;

    @MockitoBean
    AuthorDTOMapper authorDTOMapper;

    @Test
    public void testMapToReviewDTOCorrectly() {
        // arrange
        Review review = mock(Review.class);
        when(review.getName()).thenReturn("places/Chfkhk3yfk");
        when(review.getRelativePublishTimeDescription()).thenReturn("2 months ago");
        when(review.getRating()).thenReturn(5.0);
        when(review.hasText()).thenReturn(true);
        when(review.getText()).thenReturn(LocalizedText.newBuilder().setText("some review text").build());
        AuthorAttribution authorAttribution = mock(AuthorAttribution.class);
        when(review.hasAuthorAttribution()).thenReturn(true);
        when(review.getAuthorAttribution()).thenReturn(authorAttribution);
        when(authorAttribution.getDisplayName()).thenReturn("John Doe");
        when(authorDTOMapper.toAuthorDTO(any())).thenReturn(AuthorDTO.builder().displayName("John Doe").build());
        Timestamp publishTime = Timestamp.newBuilder().setSeconds(10).build();
        when(review.getPublishTime()).thenReturn(publishTime);
        when(review.getFlagContentUri()).thenReturn("https://google.com/");
        when(review.getGoogleMapsUri()).thenReturn("https://google.com/");

        // act
        ReviewDTO reviewDTO = mapper.toReviewDTO(review);

        // assert
        assertThat(reviewDTO.getName()).isEqualTo("places/Chfkhk3yfk");
        assertThat(reviewDTO.getRelativePublishTimeDescription()).isEqualTo("2 months ago");
        assertThat(reviewDTO.getRating()).isEqualTo(5.0);
        assertThat(reviewDTO.getText()).isEqualTo("some review text");
        assertThat(reviewDTO.getAuthorAttribution().getDisplayName()).isEqualTo("John Doe");
        assertThat(reviewDTO.getGoogleMapsUri()).isEqualTo("https://google.com/");
        assertThat(reviewDTO.getPublishTime()).isEqualTo("1970-01-01T00:00:10Z");
    }

    @Test
    public void shouldReturnNullWhenReviewIsNull() {
        // arrange
        Review review = null;

        // act
        ReviewDTO reviewDTO = mapper.toReviewDTO(review);

        // assert
        assertThat(reviewDTO).isNull();
    }
}