package com.carpathianapiary.niko.beekeepernotes.database.userData

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg userData: UserData)

    @Query("SELECT * from users WHERE uid = :uid LIMIT 1")
    fun getUserById(uid: String): LiveData<UserData>
}