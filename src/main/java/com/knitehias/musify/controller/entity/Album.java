package com.knitehias.musify.controller.entity;

import java.io.Serializable;

public record Album(String id, String title, String imageUrl) implements Serializable {

}
