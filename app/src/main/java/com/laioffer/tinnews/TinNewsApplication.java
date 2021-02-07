package com.laioffer.tinnews;

import android.app.Application;

import androidx.room.Room;

import com.ashokvarma.gander.Gander;
import com.ashokvarma.gander.imdb.GanderIMDB;
import com.facebook.stetho.Stetho;
import com.laioffer.tinnews.database.TinNewsDatabase;

public class TinNewsApplication extends Application {
    // Application是一个singletone，一个app是一个process，一个app里只有一个application
    // 可以有多个

    private TinNewsDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        Gander.setGanderStorage(GanderIMDB.getInstance());
        Stetho.initializeWithDefaults(this);
        database = Room
                .databaseBuilder(this, TinNewsDatabase.class, "tinnews_db")
                .build();
    }

    public TinNewsDatabase getDatabase() {
        return database;
    }

}
