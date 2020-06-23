package com.bloodbird.meal.Database;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@androidx.room.Dao

public interface SuresDao {

     @Insert
    void insert(SuresDb suresDb);

     @Update
    void update(SuresDb suresDb);

     @Delete
    void delete(SuresDb  suresDb);

    @Query("SELECT * FROM SuresDb")
    List<SuresDb> getSuresDb();

    @Insert
    void insert(VerseDb VerseDb);

    @Update
    void update(VerseDb VerseDb);

    @Delete
    void delete(VerseDb VerseDb);

    @Query("SELECT * FROM VerseDb")
    List<VerseDb> getVerseDb();

    @Query("SELECT VerseDb.id, VerseDb.surah_id, VerseDb.verse_number, VerseDb.page, VerseDb.juzNumber, VerseDb.verse, VerseDb.transcription, VerseDb.text FROM VerseDb  WHERE VerseDb.surah_id == :sure_id")
    List<VerseDb> getAyets(int sure_id);


    @Query("SELECT * FROM SuresDb  WHERE SuresDb.id == :sure_id")
    SuresDb getSurah(int sure_id);

}
