package com.carpathianapiary.beekeepernotes.database.userData

fun User.mapToUserData(uId: String): UserData {

    return UserData(
            uId,
            this.name,
            this.photoUrl,
            this.email,
            this.phoneNumberOne,
            this.phoneNumberTwo,
            this.country,
            this.city,
            this.phoneToken,
            this.type
    )
}