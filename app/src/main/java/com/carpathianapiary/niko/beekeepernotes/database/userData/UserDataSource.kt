package com.carpathianapiary.niko.beekeepernotes.database.userData

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseUser

interface UserDataSource {

    fun loadUser(firebaseUser: FirebaseUser, token: String, loadUserDataCallback: LoadUserDataCallback)

    fun getCurrentUser(loadUserDataCallback: LoadUserDataCallback?)

    fun saveUserData(userData: UserData, loadUserDataCallback: LoadUserDataCallback)

    fun getUserData(): LiveData<UserData>

    fun updateUserData(user: UserData, loadUserDataCallback: LoadUserDataCallback)

    fun deleteUserDate(user: UserData)

    interface LoadUserDataCallback {

        fun onUserLoaded(userData: UserData)

        fun onUserLoadFailed(throwable: Throwable)
    }
}