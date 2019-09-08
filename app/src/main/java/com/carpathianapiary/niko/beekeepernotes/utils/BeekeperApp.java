package com.carpathianapiary.niko.beekeepernotes.utils;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.multidex.MultiDex;
import androidx.room.Room;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.ndk.CrashlyticsNdk;
import com.carpathianapiary.niko.beekeepernotes.database.AppDatabase;
import com.carpathianapiary.niko.beekeepernotes.database.userData.UserDataRepository;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.messaging.FirebaseMessaging;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Key on 29.06.2017.
 */

public class BeekeperApp extends Application {

    private static final String TAG = "BeekeperApp";

    private static final String APP_TOPIC = "beekeper_note_group";

    private AppDatabase roomDatabase;

    private static BeekeperApp instance;

    private UserDataRepository userDataRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics(), new CrashlyticsNdk());
        intiFirestore();
        initRoomDatabase();
        initRepositories();
        instance = this;
    }

    private void initRepositories() {
        userDataRepository = UserDataRepository.Companion.getInstance();
    }

    private void intiFirestore() {
        FirebaseApp.initializeApp(this);
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.setFirestoreSettings(settings);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);
        firebaseDatabase.getReference().keepSynced(true);
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        FirebaseMessaging.getInstance().subscribeToTopic(APP_TOPIC)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.i(TAG, "Success subscribeToTopic");
                    }
                });
    }

    private void initRoomDatabase() {
        roomDatabase = Room.databaseBuilder(this,
                AppDatabase.class, "beekeperNote").build();
    }

    public static BeekeperApp getInstance() {
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public UserDataRepository getUserDataRepository() {
        return userDataRepository;
    }
}
