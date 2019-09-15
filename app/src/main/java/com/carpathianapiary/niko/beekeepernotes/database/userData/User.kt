package com.carpathianapiary.niko.beekeepernotes.database.userData
class User() {
    companion object {
        const val TYPE_NOT_EXIST = -1
        const val TYPE_ONLINE = 1
        const val TYPE_OFFLINE = 2
    }

    var name: String = ""

    var photoUrl: String = ""

    var email: String = ""

    var phoneNumberOne: String = ""

    var phoneNumberTwo: String = ""

    var country: String = ""

    var city: String = ""

    var phoneToken: String = ""

    var type: Int = TYPE_OFFLINE

    constructor(
             name: String = "",

             photoUrl: String = "",

             email: String = "",

             phoneNumberOne: String = "",

             phoneNumberTwo: String = "",

             country: String = "",

             city: String = "",

             phoneToken: String = "",

             type: Int = TYPE_OFFLINE
    ) : this() {
        this.name = name
        this.photoUrl = photoUrl

        this.email= email

        this.phoneNumberOne= phoneNumberOne

        this.phoneNumberTwo= phoneNumberTwo

        this.country= country

        this.city= city

        this.phoneToken= phoneToken

        this.type= type
    }
}