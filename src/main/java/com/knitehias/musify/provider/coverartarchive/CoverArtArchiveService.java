package com.knitehias.musify.provider.coverartarchive;

import com.knitehias.musify.provider.coverartarchive.entity.CoverArtArchiveResponse;
import com.knitehias.musify.provider.coverartarchive.entity.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
public class CoverArtArchiveService {
    private static final String FORMATTER = "http://coverartarchive.org/release-group/%s";
    private final RestTemplate restTemplate;

    @Autowired
    public CoverArtArchiveService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getImageUrlById(String id) {
        try {

            return Objects.requireNonNull(restTemplate.getForEntity(String.format(FORMATTER, id), CoverArtArchiveResponse.class)
                            .getBody())
                    .images()
                    .stream()
                    .filter(image -> image.approved() && image.front())
                    .findFirst()
                    .map(Image::image)
                    .orElse("");
        } catch (HttpClientErrorException httpClientErrorException) {
            if (404 == httpClientErrorException.getStatusCode().value()) {
                return "";
            }
            return null;
        }

    }
}
