package com.bloodbird.meal.Models;

import java.util.List;

public class Sure {
      public int id,verse_count,pageNumber;
     public  String name,slug;
     public  Verses zero;
     public List<Verses> verses;
     public Audio audio;

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

     public Verses getZero() {
          return zero;
     }

     public void setZero(Verses zero) {
          this.zero = zero;
     }

     public List<Verses> getVerses() {
          return verses;
     }

     public void setVerses(List<Verses> verses) {
          this.verses = verses;
     }
}
