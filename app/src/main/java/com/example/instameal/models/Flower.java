package com.example.instameal.models;

public class Flower {
    private int id;
    private String flower_name;
    private String color_flower;
    private String flower_meaning;
    private String image_url;

    public Flower(int id, String flower_name, String color_flower, String flower_meaning, String image_url) {
        this.id = id;
        this.flower_name = flower_name;
        this.color_flower = color_flower;
        this.flower_meaning = flower_meaning;
        this.image_url = image_url;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return flower_name;
    }

    public String getColor() {
        return color_flower;
    }

    public String getCombinedTitle() {
        return color_flower + " " + flower_name;
    }

    public String getMeaning() {
        return flower_meaning;
    }

    public String getImageUrl() {
        return image_url;
    }
}

