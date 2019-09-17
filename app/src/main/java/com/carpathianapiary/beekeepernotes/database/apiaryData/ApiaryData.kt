package com.carpathianapiary.beekeepernotes.database.apiaryData

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "apiaries")
data class ApiaryData(
        @PrimaryKey
        var id: String = "",
        
        var name: String = "",
        @ColumnInfo(name = "location_id")
        var locationId: String = "",
        
        var info: String = "",
        
        var note: String = ""
) {
}