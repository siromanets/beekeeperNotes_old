package com.carpathianapiary.niko.beekeepernotes.database.userData

import android.os.FileObserver.DELETE
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg userData: UserData)

    @Query("SELECT * from users LIMIT 1")
    fun getUserData(): LiveData<UserData>

    @Query("SELECT * from users LIMIT 1")
    fun getUser(): UserData?

    @Query("DELETE FROM users")
    fun deleteUser()
}