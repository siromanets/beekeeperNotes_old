package com.carpathianapiary.niko.beekeepernotes.database.userData

data class User(

        var name: String = "",

        var photoUrl: String = "",

        var email: String = "",

        var phoneNumberOne: String = "",

        var phoneNumberTwo: String = "",

        var country: String = "",

        var city: String = "",

        var phoneToken: String = "",

        var type: Int

) {
}