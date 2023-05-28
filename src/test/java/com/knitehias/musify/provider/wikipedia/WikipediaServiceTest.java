package com.knitehias.musify.provider.wikipedia;

import com.knitehias.musify.provider.wikipedia.entity.WikipediaResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class WikipediaServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private WikipediaService wikipediaService;

    @Test
    void testReturnDescription_WhenServiceWorks() {
        Mockito.when(restTemplate.getForEntity("https://en.wikipedia.org/api/rest_v1/page/summary/Michael Jackson", WikipediaResponse.class))
                .thenReturn(ResponseEntity.ok(new WikipediaResponse("Michael Jackson", "<h>Legend</h>")));
        Assertions.assertEquals("<h>Legend</h>", wikipediaService.getDescriptionByTitle("Michael Jackson"));
    }
}