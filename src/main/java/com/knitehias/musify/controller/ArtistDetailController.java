package com.knitehias.musify.controller;

import com.knitehias.musify.controller.entity.ArtistDetail;
import io.micrometer.core.annotation.Timed;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;


@RestController
@Timed
public class ArtistDetailController {

    private final ArtistDetailExecutor artistDetailExecutor;

    public ArtistDetailController(ArtistDetailExecutor artistDetailExecutor) {
        this.artistDetailExecutor = artistDetailExecutor;
    }

    @GetMapping("/musify/music-artist/details/{mbid}")
    public ResponseEntity<ArtistDetail> getArtistDetailById(@PathVariable String mbid) {
        if (!UUIDValidator.isValidUUID(mbid)) {
            return ResponseEntity.badRequest().body(null);
        }
        try {
            return ResponseEntity.ok(this.artistDetailExecutor.getArtistDetailById(mbid));
        } catch (HttpClientErrorException e) {
            if (404 == e.getStatusCode().value()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.noContent().build();
        }
    }
}
