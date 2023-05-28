package com.knitehias.musify.provider.wikidata;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;


class CustomerWikiDataResponseDeserializerTest {

    CustomerWikiDataResponseDeserializer customerWikiDataResponseDeserializer
            = new CustomerWikiDataResponseDeserializer("Q2831");
    String raw = """
            {
              "entities": {
                "Q2831": {
                  "pageid": 3813,
                  "ns": 0,
                  "title": "Q2831",
                  "lastrevid": 1901514937,
                  "modified": "2023-05-23T15:28:04Z",
                  "type": "item",
                  "id": "Q2831",
                  "sitelinks": {
                    "emlwiki": {
                      "site": "emlwiki",
                      "title": "Michael Jackson",
                      "badges": [],
                      "url": "https://eml.wikipedia.org/wiki/Michael_Jackson"
                    },
                    "enwiki": {
                      "site": "enwiki",
                      "title": "Michael Jackson",
                      "badges": [
                        "Q17437796"
                      ],
                      "url": "https://en.wikipedia.org/wiki/Michael_Jackson"
                    },
                    "enwikinews": {
                      "site": "enwikinews",
                      "title": "Category:Michael Jackson",
                      "badges": [],
                      "url": "https://en.wikinews.org/wiki/Category:Michael_Jackson"
                    },
                    "zuwiki": {
                      "site": "zuwiki",
                      "title": "Michael Jackson",
                      "badges": [],
                      "url": "https://zu.wikipedia.org/wiki/Michael_Jackson"
                    }
                  }
                }
              }
            }""";

    @Test
    void testDeserializerDynamicField() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonFactory factory = mapper.getFactory();
        JsonParser parser = factory.createParser(raw);
        Assertions.assertEquals("https://en.wikipedia.org/wiki/Michael_Jackson", customerWikiDataResponseDeserializer.deserialize(parser, null).entities().q().sitelinks().enwiki().url());
    }


}