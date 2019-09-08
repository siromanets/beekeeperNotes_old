package com.carpathianapiary.niko.beekeepernotes.database.userData

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserData(

        @PrimaryKey
        var uid: String,

        var name: String? = null,

        @ColumnInfo(name = "photo_url")
        var photoUrl: String? = null,

        var email: String? = null,

        @ColumnInfo(name = "phone_number_one")
        var phoneNumberOne: String? = null,

        @ColumnInfo(name = "phone_number_two")
        var phoneNumberTwo: String? = null,

        var country: String? = null,

        var city: String? = null,

        var token: String? = null,

        val type: Int = 0

) {

    override fun toString(): String {
        return "UserData{" +
                "uid='" + uid + '\''.toString() +
                ", name='" + name + '\''.toString() +
                ", photoUrl='" + photoUrl + '\''.toString() +
                ", email='" + email + '\''.toString() +
                ", phoneNumberOne='" + phoneNumberOne + '\''.toString() +
                ", phoneNumberTwo='" + phoneNumberTwo + '\''.toString() +
                ", country='" + country + '\''.toString() +
                ", city='" + city + '\''.toString() +
                '}'.toString()
    }
}
