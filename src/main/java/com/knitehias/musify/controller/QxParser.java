package com.knitehias.musify.controller;

import java.util.function.Function;

public class QxParser {

    public static final Function<String, String> GETq = (str) -> {
        String[] parts = str.split("/");
        if (parts.length == 0) return "";
        else return parts[parts.length - 1];
    };
}
