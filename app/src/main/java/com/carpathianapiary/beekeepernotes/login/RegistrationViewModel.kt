package com.carpathianapiary.beekeepernotes.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.carpathianapiary.beekeepernotes.database.userData.User
import com.carpathianapiary.beekeepernotes.database.userData.UserData
import com.carpathianapiary.beekeepernotes.database.userData.UserDataRepository
import com.carpathianapiary.beekeepernotes.database.userData.UserDataSource
import com.carpathianapiary.beekeepernotes.utils.LogUtils
import com.carpathianapiary.beekeepernotes.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.iid.FirebaseInstanceId

private const val TAG = "RegistrationViewModel"

class RegistrationViewModel : ViewModel() {

    private val userDataRepository: UserDataRepository = UserDataRepository.instance

    private val userData: MutableLiveData<Resource<UserData>> = MutableLiveData()

    private val userType: MutableLiveData<Int> = MutableLiveData()

    init {
        getCurrentUser()
    }

    private fun getCurrentUser() {
        userDataRepository.getCurrentUser(object : UserDataSource.LoadUserDataCallback {
            override fun onUserLoaded(userData: UserData) {
                    userType.value = userData.type
            }

            override fun onUserLoadFailed(throwable: Throwable) {
                userType.value = User.TYPE_NOT_EXIST
            }
        })
    }


    fun saveUser() {
        userData.value = Resource.loading()
        val firebaseUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        firebaseUser?.let {
                            loadUser(it, task.result!!.token)
                        }
                    }
                }
    }

    fun getUserData(): LiveData<Resource<UserData>> {
        return userData
    }

    fun getUserType(): LiveData<Int> {
        return userType
    }

    fun createAnonymousUser() {
        userData.value = Resource.loading()
        userDataRepository.saveUserData(UserData(
                uid = "Anonimous",
                name = "Anonimous",
                type = User.TYPE_OFFLINE
        ), object : UserDataSource.LoadUserDataCallback {
            override fun onUserLoaded(user: UserData) {
                userData.value = Resource.success(user)
            }

            override fun onUserLoadFailed(throwable: Throwable) {
                LogUtils.e(TAG, "onUserLoadFailed" + throwable.message)
            }

        })
    }

    private fun loadUser(user: FirebaseUser, token: String) {
        userDataRepository.loadUser(user, token, object : UserDataSource.LoadUserDataCallback {
            override fun onUserLoaded(user: UserData) {
                userData.value = Resource.success(user)
            }

            override fun onUserLoadFailed(throwable: Throwable) {
                LogUtils.e(TAG, "onUserLoadFailed" + throwable.message)
            }
        })
    }
}
