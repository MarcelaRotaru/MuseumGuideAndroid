package com.example.museumguideandroid;

public class Picture {

    String url;
    String title;
    String description;
    String author;


    public Picture(String url, String title, String description, String author) {
        this.url = url;
        this.title = title;
        this.description = description;
        this.author = author;
    }


    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }


}
