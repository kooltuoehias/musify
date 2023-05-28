package com.knitehias.musify.controller;

import com.knitehias.musify.controller.entity.Album;
import com.knitehias.musify.controller.entity.ArtistDetail;
import com.knitehias.musify.provider.coverartarchive.CoverArtArchiveService;
import com.knitehias.musify.provider.musicbrainz.MusicBrainzService;
import com.knitehias.musify.provider.musicbrainz.entity.MusicBrainzResponse;
import com.knitehias.musify.provider.musicbrainz.entity.Relation;
import com.knitehias.musify.provider.musicbrainz.entity.RelationUrl;
import com.knitehias.musify.provider.wikidata.WikiDataService;
import com.knitehias.musify.provider.wikipedia.WikipediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@Component
public class ArtistDetailExecutor {

    private final MusicBrainzService musicBrainzService;
    private final WikiDataService wikiDataService;
    private final WikipediaService wikipediaService;
    private final CoverArtArchiveService coverArtArchiveService;

    @Autowired
    public ArtistDetailExecutor(MusicBrainzService musicBrainzService, WikiDataService wikiDataService, WikipediaService wikipediaService, CoverArtArchiveService coverArtArchiveService) {
        this.musicBrainzService = musicBrainzService;
        this.wikiDataService = wikiDataService;
        this.wikipediaService = wikipediaService;
        this.coverArtArchiveService = coverArtArchiveService;
    }

    @Cacheable("artists")
    public ArtistDetail getArtistDetailById(String mbid) {
        MusicBrainzResponse musicBrainzResponse = musicBrainzService.getMusicBrainzById(mbid);
        CompletableFuture<String> descriptionTask = musicBrainzResponse.relations()
                .stream()
                .filter(relation -> relation.type().equals("wikidata"))
                .findFirst()
                .map(Relation::url)
                .map(RelationUrl::resource)
                .map(QxParser.GETq)
                .map(wikiDataService::getTitleByQx)
                .map(title -> CompletableFuture.supplyAsync(() -> wikipediaService.getDescriptionByTitle(title)))
                .orElse(CompletableFuture.supplyAsync(() -> ""));
        List<CompletableFuture<Album>> albumsTask = musicBrainzResponse.releaseGroup()
                .stream().map(releaseGroup ->
                        CompletableFuture.supplyAsync(
                                () -> new Album(releaseGroup.id(), releaseGroup.title(), coverArtArchiveService.getImageUrlById(releaseGroup.id()))
                        )).toList();

        CompletableFuture.allOf(Stream.concat(
                Stream.of(descriptionTask), albumsTask.stream()
        ).toArray(CompletableFuture[]::new)).join();

        return new ArtistDetail.ArtistDetailBuilder()
                .withMbid(mbid)
                .withName(musicBrainzResponse.name())
                .withGender(musicBrainzResponse.gender())
                .withCountry(musicBrainzResponse.country())
                .withDescription(descriptionTask.join())
                .withDisambiguation(musicBrainzResponse.disambiguation())
                .withAlbums(albumsTask.stream()
                        .map(CompletableFuture::join)
                        .toList())
                .build();
    }
}