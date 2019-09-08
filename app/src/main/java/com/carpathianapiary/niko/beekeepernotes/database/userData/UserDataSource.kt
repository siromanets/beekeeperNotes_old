package com.carpathianapiary.niko.beekeepernotes.database.userData

import androidx.lifecycle.LiveData

interface UserDataSource {

    fun loadUser(userId: String, token: String)

    fun saveUserData(userData: UserData)

    fun getUserData(userId: String): LiveData<User>

    fun updateUserData(user: User)

    fun deleteUserDate(user: User)
}