package com.example.instameal.models;

public class Bouquet {
    private int id;
    private String bouquet_name;
    private String description;
    private String people;
    private String event;
    private String flowers;
    private String image_url;
    private int price;

    public Bouquet(int id, String bouquet_name, String description, String people, String event, String flowers, String image_url, int price) {
        this.id = id;
        this.bouquet_name = bouquet_name;
        this.description = description;
        this.people = people;
        this.event = event;
        this.flowers = flowers;
        this.image_url = image_url;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return bouquet_name;
    }

    public String getMeaning() {
        return description;
    }

    public String getEvent() {
        return event;
    }

    public String getPeople() {
        return people;
    }

    public String getFlowers() {
        return flowers;
    }

    public String getImageUrl() {
        return image_url;
    }

    public int getPrice() {
        return price;
    }
}

