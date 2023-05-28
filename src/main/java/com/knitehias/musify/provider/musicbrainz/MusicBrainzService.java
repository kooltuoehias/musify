package com.knitehias.musify.provider.musicbrainz;

import com.knitehias.musify.provider.musicbrainz.entity.MusicBrainzResponse;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MusicBrainzService {
    private static final String FORMATTER = "http://musicbrainz.org/ws/2/artist/%s?&fmt=json&inc=url-rels+release-groups";
    private final RestTemplate restTemplate;
    @Autowired
    private MeterRegistry registry;

    @Autowired
    public MusicBrainzService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public MusicBrainzResponse getMusicBrainzById(String mbid) {
        ResponseEntity<MusicBrainzResponse> musicBrainzResponse = restTemplate.getForEntity(String.format(FORMATTER, mbid), MusicBrainzResponse.class);
        return musicBrainzResponse.getBody();
    }
}
