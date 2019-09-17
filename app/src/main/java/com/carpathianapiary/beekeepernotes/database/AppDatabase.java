package com.carpathianapiary.beekeepernotes.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.carpathianapiary.beekeepernotes.database.apiaryData.ApiaryData;
import com.carpathianapiary.beekeepernotes.database.userData.UserDao;
import com.carpathianapiary.beekeepernotes.database.userData.UserData;

@Database(entities = {UserData.class, ApiaryData.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    public abstract ApiaryData apiaryDao();
}
