package com.laioffer.tinnews;

import android.app.Application;

import com.ashokvarma.gander.Gander;
import com.ashokvarma.gander.imdb.GanderIMDB;

public class TinNewsApplication extends Application {
    // Application是一个singletone，一个app是一个process，一个app里只有一个application
    // 可以有多个
    @Override
    public void onCreate() {
        super.onCreate();
        Gander.setGanderStorage(GanderIMDB.getInstance());
    }
}
