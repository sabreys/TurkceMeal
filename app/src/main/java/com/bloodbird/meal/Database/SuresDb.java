package com.bloodbird.meal.Database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.bloodbird.meal.Models.Audio;
import com.bloodbird.meal.Models.Verses;

import java.util.List;

@Entity(tableName = "SuresDb")
public class SuresDb {
    @PrimaryKey
    public int id;
    public int verse_count;
    public int pageNumber;
    public String name;
    public String slug;
   // public Verses zero;
    //public List<Verses> verses;
    public String audioUrl;

    public SuresDb(int id, int verse_count, int pageNumber, String name, String slug, String audioUrl) {
        this.id = id;
        this.verse_count = verse_count;
        this.pageNumber = pageNumber;
        this.name = name;
        this.slug = slug;

        this.audioUrl = audioUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVerse_count() {
        return verse_count;
    }

    public void setVerse_count(int verse_count) {
        this.verse_count = verse_count;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
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


    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }
}
