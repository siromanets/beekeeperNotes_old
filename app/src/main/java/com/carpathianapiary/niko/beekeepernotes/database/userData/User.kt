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

    companion object {
        const val TYPE_NOT_EXIST = -1
        const val TYPE_ONLINE = 1
        const val TYPE_OFFLINE = 2
    }
}