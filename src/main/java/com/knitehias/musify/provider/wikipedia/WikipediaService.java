package com.knitehias.musify.provider.wikipedia;

import com.knitehias.musify.provider.wikipedia.entity.WikipediaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
public class WikipediaService {
    private static final String FORMATTER = "https://en.wikipedia.org/api/rest_v1/page/summary/%s";
    private final RestTemplate restTemplate;

    @Autowired
    public WikipediaService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getDescriptionByTitle(String title) {
        return Objects.requireNonNull(restTemplate.getForEntity(String.format(FORMATTER, title), WikipediaResponse.class).getBody()).extract_html();
    }
}
