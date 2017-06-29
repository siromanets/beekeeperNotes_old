package com.example.key.beekeepernote;

import android.app.Application;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Key on 29.06.2017.
 */

public class BeekeperApp extends Application {
    FirebaseDatabase fb;
    @Override
    public void onCreate() {
        super.onCreate();
        if (!FirebaseApp.getApps(this).isEmpty())
        {
            fb = FirebaseDatabase.getInstance();
            fb.setPersistenceEnabled(true);
            fb.getReference().keepSynced(true);
        }
    }
}
