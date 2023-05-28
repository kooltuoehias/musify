package com.knitehias.musify.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;


class UUIDValidatorTest {

    @Test
    public void testInvalidId() {
        Assertions.assertFalse(UUIDValidator.isValidUUID("1a2b3c-3432"));
    }

    @Test
    void testValidUUID() {
        Assertions.assertTrue(UUIDValidator.isValidUUID("f27ec8db-af05-4f36-916e-3d57f91ecf5e"));
        Assertions.assertTrue(UUIDValidator.isValidUUID(UUID.randomUUID().toString()));
    }

}