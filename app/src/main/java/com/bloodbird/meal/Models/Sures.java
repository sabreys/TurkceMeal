package com.bloodbird.meal.Models;

   public class Sures {
    public int id;
    public String name;
    public String slug;
    public int verse_count;

    public Sures(int id, String name, String slug, int verse_count) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.verse_count = verse_count;
    }

    public Sures() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public int getVerse_count() {
        return verse_count;
    }

    public void setVerse_count(int verse_count) {
        this.verse_count = verse_count;
    }
}
