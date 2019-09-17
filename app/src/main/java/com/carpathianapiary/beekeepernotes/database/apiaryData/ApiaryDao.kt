package com.carpathianapiary.beekeepernotes.database.apiaryData

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ApiaryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg apiaryData: ApiaryData)

    @Query("SELECT * from apiaries")
    fun getApiariesData(): LiveData<List<ApiaryData>>

    @Query("SELECT * from apiaries")
    fun getApiaries(): List<ApiaryData>

    @Query("DELETE FROM apiaries")
    fun deleteAll()
    
    @Delete
    fun delete(vararg apiaryData: ApiaryData)
}