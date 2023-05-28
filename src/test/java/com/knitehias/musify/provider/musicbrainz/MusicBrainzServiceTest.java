package com.knitehias.musify.provider.musicbrainz;


import com.knitehias.musify.provider.musicbrainz.entity.MusicBrainzResponse;
import com.knitehias.musify.provider.musicbrainz.entity.Relation;
import com.knitehias.musify.provider.musicbrainz.entity.ReleaseGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
class MusicBrainzServiceTest {

    @Mock
    RestTemplate restTemplate;
    @InjectMocks
    MusicBrainzService musicBrainzService;

    @Test
    void testGettingFullResponse() {
        Mockito.when(restTemplate.getForEntity("http://musicbrainz.org/ws/2/artist/98F803FE-2030-467C-AF95-2E30CA7E0AEC?&fmt=json&inc=url-rels+release-groups", MusicBrainzResponse.class))
                .thenReturn(ResponseEntity.ok(new MusicBrainzResponse("98F803FE-2030-467C-AF95-2E30CA7E0AEC", "name", "gender", "Japan", "disambiguation", new ArrayList<ReleaseGroup>(), new ArrayList<Relation>())));
        Assertions.assertEquals("Japan", musicBrainzService.getMusicBrainzById("98F803FE-2030-467C-AF95-2E30CA7E0AEC").country());
    }
}