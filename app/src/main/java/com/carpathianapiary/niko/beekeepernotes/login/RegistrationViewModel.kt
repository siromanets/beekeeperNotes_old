package com.carpathianapiary.niko.beekeepernotes.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.carpathianapiary.niko.beekeepernotes.database.userData.UserData
import com.carpathianapiary.niko.beekeepernotes.database.userData.UserDataRepository
import com.carpathianapiary.niko.beekeepernotes.database.userData.UserDataSource
import com.carpathianapiary.niko.beekeepernotes.utils.BeekeperApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.iid.FirebaseInstanceId

class RegistrationViewModel : ViewModel() {

    private val userDataRepository: UserDataRepository = BeekeperApp.getInstance().userDataRepository

    val userData: LiveData<UserData> = userDataRepository.getUserData()


    fun saveUser() {
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

    private fun loadUser(user: FirebaseUser, token: String) {
        userDataRepository.loadUser(user, token, object : UserDataSource.LoadUserDataCallback {
            override fun onUserLoaded(userData: UserData) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onUserLoadFailed(throwable: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }


        })
    }
}
