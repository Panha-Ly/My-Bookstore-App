package com.lypanha.mybookstore.model;

public class Book {
    private final String seriesId, name, author,publicationDate, description, price;
    private final int id, imageId;

    public Book(int id, String seriesId, String name, String author, String publicationDate, String description, String price, int imageId) {
        this.id = id;
        this.seriesId = seriesId;
        this.name = name;
        this.author = author;
        this.publicationDate = publicationDate;
        this.description = description;
        this.price = price;
        this.imageId = imageId;

    }


    public String getSeriesId() {
        return seriesId;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }


    public int getImageId() {
        return imageId;
    }
}
