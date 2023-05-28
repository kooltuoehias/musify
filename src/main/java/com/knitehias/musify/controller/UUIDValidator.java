package com.knitehias.musify.controller;

import java.util.UUID;

public class UUIDValidator {
    public static boolean isValidUUID(String uuid) {
        try {
            UUID.fromString(uuid);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
