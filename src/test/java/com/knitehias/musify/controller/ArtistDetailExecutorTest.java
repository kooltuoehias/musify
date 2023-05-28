package com.knitehias.musify.controller;


import com.knitehias.musify.controller.entity.Album;
import com.knitehias.musify.controller.entity.ArtistDetail;
import com.knitehias.musify.provider.coverartarchive.CoverArtArchiveService;
import com.knitehias.musify.provider.musicbrainz.MusicBrainzService;
import com.knitehias.musify.provider.musicbrainz.entity.MusicBrainzResponse;
import com.knitehias.musify.provider.musicbrainz.entity.Relation;
import com.knitehias.musify.provider.musicbrainz.entity.RelationUrl;
import com.knitehias.musify.provider.musicbrainz.entity.ReleaseGroup;
import com.knitehias.musify.provider.wikidata.WikiDataService;
import com.knitehias.musify.provider.wikipedia.WikipediaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class ArtistDetailExecutorTest {

    @Mock
    private MusicBrainzService musicBrainzService;
    @Mock
    private WikiDataService wikiDataService;
    @Mock
    private WikipediaService wikipediaService;
    @Mock
    private CoverArtArchiveService coverArtArchiveService;

    @InjectMocks
    private ArtistDetailExecutor artistDetailExecutor;


    @Test
    void testGeneratingFullArtistDetailResponse() {
        ArtistDetail expect = new ArtistDetail.ArtistDetailBuilder()
                .withName("name")
                .withMbid("98F803FE-2030-467C-AF95-2E30CA7E0AEC")
                .withGender("gender")
                .withCountry("Japan")
                .withDescription("description")
                .withDisambiguation("Legend")
                .withAlbums(List.of(new Album("releaseGroupId", "releaseGroupTitle", "albumImageUrl")))
                .build();
        Mockito.when(musicBrainzService.getMusicBrainzById("98F803FE-2030-467C-AF95-2E30CA7E0AEC"))
                .thenReturn(new MusicBrainzResponse("98F803FE-2030-467C-AF95-2E30CA7E0AEC", "name", "gender", "Japan", "Legend", List.of(new ReleaseGroup("releaseGroupId", "releaseGroupTitle")), List.of(new Relation("wikidata", new RelationUrl("relationUrlId", "wikidata/url/Q3")))));
        Mockito.when(wikiDataService.getTitleByQx("Q3"))
                .thenReturn("Michael Jackson");
        Mockito.when(wikipediaService.getDescriptionByTitle("Michael Jackson"))
                .thenReturn("description");
        Mockito.when(coverArtArchiveService.getImageUrlById("releaseGroupId"))
                .thenReturn("albumImageUrl");
        ArtistDetail actual = artistDetailExecutor.getArtistDetailById("98F803FE-2030-467C-AF95-2E30CA7E0AEC");
        Assertions.assertEquals(expect, actual);
    }
}