package com.knitehias.musify.provider.musicbrainz.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record MusicBrainzResponse(String mbid, String name, String gender, String country, String disambiguation,
                                  @JsonProperty("release-groups") List<ReleaseGroup> releaseGroup,
                                  List<Relation> relations) {

}
