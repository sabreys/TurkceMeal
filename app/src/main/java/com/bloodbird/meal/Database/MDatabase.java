package com.bloodbird.meal.Database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
@androidx.room.Database(entities = {SuresDb.class,VerseDb.class},version = 1)
public abstract class MDatabase extends RoomDatabase {
  private static MDatabase instance;
  public abstract  SuresDao SuresDao();

    public static synchronized MDatabase getInstance(Context context) {
        if(instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext()
            , MDatabase.class,"database").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        }
        return instance;
    }

}
