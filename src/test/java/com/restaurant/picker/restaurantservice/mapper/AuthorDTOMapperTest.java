package com.restaurant.picker.restaurantservice.mapper;

import com.google.maps.places.v1.AuthorAttribution;
import com.restaurant.picker.restaurantservice.dto.AuthorDTO;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuthorDTOMapperTest {

    private final AuthorDTOMapper authorDTOMapper = new AuthorDTOMapper();

    @Test
    public void testMapAuthorDTOCorrectly() {
        // arrange
        AuthorAttribution authorAttribution = mock(AuthorAttribution.class);
        when(authorAttribution.getDisplayName()).thenReturn("John Doe");
        when(authorAttribution.getUri()).thenReturn("https://www.google.com");
        when(authorAttribution.getPhotoUri()).thenReturn("https://www.google.com/some-photo.jpg");

        // act
        AuthorDTO authorDTO = authorDTOMapper.toAuthorDTO(authorAttribution);

        // assert
        assertThat(authorDTO.getDisplayName()).isEqualTo("John Doe");
        assertThat(authorDTO.getUri()).isEqualTo("https://www.google.com");
        assertThat(authorDTO.getPhotoUri()).isEqualTo("https://www.google.com/some-photo.jpg");
    }

    @Test
    public void testMapAuthorDTOCorrectlyWithMissingValues() {
        // arrange
        AuthorAttribution authorAttribution = mock(AuthorAttribution.class);
        when(authorAttribution.getDisplayName()).thenReturn("John Doe");
        when(authorAttribution.getUri()).thenReturn("");
        when(authorAttribution.getPhotoUri()).thenReturn("");

        // act
        AuthorDTO authorDTO = authorDTOMapper.toAuthorDTO(authorAttribution);

        // assert
        assertThat(authorDTO.getDisplayName()).isEqualTo("John Doe");
        assertThat(authorDTO.getUri()).isEqualTo("");
        assertThat(authorDTO.getPhotoUri()).isEqualTo("");
    }

    @Test
    public void shouldReturnNullWhenObjectIsNull() {
        // arrange
        AuthorAttribution authorAttribution = null;

        // act
        AuthorDTO authorDTO = authorDTOMapper.toAuthorDTO(authorAttribution);

        // assert
        assertThat(authorDTO).isNull();
    }
}