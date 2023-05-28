package com.knitehias.musify.controller.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class ArtistDetail implements Serializable {

    @JsonProperty
    private String mbid;

    @JsonProperty
    private String name;

    @JsonProperty
    private String gender;

    @JsonProperty
    private String country;

    @JsonProperty
    private String disambiguation;

    @JsonProperty
    private String description;

    @JsonProperty
    private List<Album> albums;

    private ArtistDetail() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArtistDetail that = (ArtistDetail) o;

        if (!Objects.equals(mbid, that.mbid)) return false;
        if (!Objects.equals(name, that.name)) return false;
        if (!Objects.equals(gender, that.gender)) return false;
        if (!Objects.equals(country, that.country)) return false;
        if (!Objects.equals(disambiguation, that.disambiguation))
            return false;
        if (!Objects.equals(description, that.description)) return false;
        return Objects.equals(albums, that.albums);
    }

    @Override
    public int hashCode() {
        int result = mbid != null ? mbid.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (disambiguation != null ? disambiguation.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (albums != null ? albums.hashCode() : 0);
        return result;
    }

    public static class ArtistDetailBuilder {
        private final ArtistDetail artistDetail = new ArtistDetail();

        public ArtistDetailBuilder() {
        }

        public ArtistDetailBuilder withName(String name) {
            this.artistDetail.name = name;
            return this;
        }

        public ArtistDetailBuilder withCountry(String country) {
            this.artistDetail.country = country;
            return this;
        }

        public ArtistDetailBuilder withGender(String gender) {
            this.artistDetail.gender = gender;
            return this;
        }

        public ArtistDetailBuilder withMbid(String mbid) {
            this.artistDetail.mbid = mbid;
            return this;
        }

        public ArtistDetailBuilder withDescription(String description) {
            this.artistDetail.description = description;
            return this;
        }

        public ArtistDetailBuilder withDisambiguation(String disambiguation) {
            this.artistDetail.disambiguation = disambiguation;
            return this;
        }

        public ArtistDetailBuilder withAlbums(List<Album> albums) {
            this.artistDetail.albums = albums;
            return this;
        }

        public ArtistDetail build() {
            return this.artistDetail;
        }
    }
}
