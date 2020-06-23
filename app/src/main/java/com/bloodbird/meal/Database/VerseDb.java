package com.bloodbird.meal.Database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.bloodbird.meal.Models.Translation;

@Entity
public
class VerseDb {
    @PrimaryKey
    public int id;
    public int surah_id;
    public int verse_number;
    public int page;
    public int juzNumber;
    public   String verse;
    public   String  transcription;
    //public Translation translation;
    public String text;


    public VerseDb(int id, int surah_id, int verse_number, int page, int juzNumber, String verse, String transcription, String text) {
        this.id = id;
        this.surah_id = surah_id;
        this.verse_number = verse_number;
        this.page = page;
        this.juzNumber = juzNumber;
        this.verse = verse;
        this.transcription = transcription;
        this.text = text;
    }
}
