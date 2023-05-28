package com.knitehias.musify.provider.wikidata;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.knitehias.musify.provider.wikidata.entity.*;

import java.io.IOException;

public class CustomerWikiDataResponseDeserializer extends JsonDeserializer<WikiDataResponse> {
    private final String qX;

    public CustomerWikiDataResponseDeserializer(String qNumber) {
        this.qX = qNumber;
    }

    @Override
    public WikiDataResponse deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        JsonNode enwiki = node.findPath("entities")
                .findPath(qX)
                .findPath("sitelinks")
                .findPath("enwiki");
        String site = enwiki.get("site").asText("");
        String title = enwiki.get("title").asText("");
        String url = enwiki.get("url").asText("");

        return new WikiDataResponse(new Entities(new Q(new Sitelinks(new Sitelink(site, title, url)))));
    }
}