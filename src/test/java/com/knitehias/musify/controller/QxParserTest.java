package com.knitehias.musify.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;


class QxParserTest {

    @Test
    void testGetQnumberFromUrl() {
        Assertions.assertEquals("Q1234", Stream.of("https://www.wikidata.org/wiki/Q1234")
                .map(QxParser.GETq)
                .toList()
                .get(0));
    }
}