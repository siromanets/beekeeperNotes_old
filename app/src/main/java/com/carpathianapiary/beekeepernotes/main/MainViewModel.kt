package com.carpathianapiary.beekeepernotes.main

import android.app.Application
import com.carpathianapiary.beekeepernotes.BaseViewModel
import com.carpathianapiary.beekeepernotes.database.userData.UserData
import com.carpathianapiary.beekeepernotes.database.userData.UserDataRepository
import com.carpathianapiary.beekeepernotes.database.userData.UserDataSource

class MainViewModel(application: Application) : BaseViewModel(application) {

    private val userDataReport = UserDataRepository.instance

    var userData: UserData? = null

    init {
        getUserData()
    }

    private fun getUserData() {
        userDataReport.getCurrentUser(object : UserDataSource.LoadUserDataCallback {
            override fun onUserLoaded(user: UserData) {
                userData = user
            }

            override fun onUserLoadFailed(throwable: Throwable) {
                invalidUserDataLiveData.value = throwable
            }
        })
    }

    fun createNewBeehave() {
        // тут треба зробити метод який буде робити запис в базу з новою пасікою
        // для цього треба створити клас репозиторій який матиме доступ до FireStore
    }
}
