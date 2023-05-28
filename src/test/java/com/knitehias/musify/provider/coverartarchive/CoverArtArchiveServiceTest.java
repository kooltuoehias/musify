package com.knitehias.musify.provider.coverartarchive;

import com.knitehias.musify.provider.coverartarchive.entity.CoverArtArchiveResponse;
import com.knitehias.musify.provider.coverartarchive.entity.Image;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
class CoverArtArchiveServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CoverArtArchiveService coverArtArchiveService;

    @Test
    void testWhenHavingFrontCover_thenReturnCorrectImageUrl() {
        CoverArtArchiveResponse coverArtArchiveResponse = new CoverArtArchiveResponse(Stream.of(
                new Image(true, true, " this is a front cover.jpeg"),
                new Image(true, false, "not here"),
                new Image(false, false, " not here again")

        ).toList());
        Mockito
                .when(restTemplate.getForEntity("http://coverartarchive.org/release-group/f27ec8db-af05-4f36-916e-3d57f91ecf5e", CoverArtArchiveResponse.class))
                .thenReturn(ResponseEntity.ok(coverArtArchiveResponse));
        Assertions.assertEquals(" this is a front cover.jpeg",
                coverArtArchiveService.getImageUrlById("f27ec8db-af05-4f36-916e-3d57f91ecf5e"));

    }

    @Test
    void testWhenCoverArtArchiveReturn404_thenReturnEmptyString() {
        Mockito
                .when(restTemplate.getForEntity("http://coverartarchive.org/release-group/f27ec8db-af05-4f36-916e-3d57f91ecf5e", CoverArtArchiveResponse.class))
                .thenThrow(new HttpClientErrorException(HttpStatusCode.valueOf(404), "Not Found"));
        Assertions.assertEquals("", coverArtArchiveService.getImageUrlById("f27ec8db-af05-4f36-916e-3d57f91ecf5e"));

    }

}