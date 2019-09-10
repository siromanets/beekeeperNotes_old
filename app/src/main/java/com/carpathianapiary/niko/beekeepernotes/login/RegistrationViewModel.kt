package com.carpathianapiary.niko.beekeepernotes.login

import androidx.lifecycle.ViewModel
import com.carpathianapiary.niko.beekeepernotes.database.userData.UserDataRepository
import com.carpathianapiary.niko.beekeepernotes.utils.BeekeperApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.iid.FirebaseInstanceId

class RegistrationViewModel : ViewModel() {

    val userDataRepository: UserDataRepository by lazy { BeekeperApp.getInstance().userDataRepository }

    fun saveUser() {
        val firebaseUser : FirebaseUser? = FirebaseAuth.getInstance().currentUser
        FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        firebaseUser?.let {
                            loadUser(it, task.result!!.token)}
                    }
                }
    }

    private fun loadUser(user: FirebaseUser, token: String) {
        userDataRepository.loadUser(user, token)
    }
}
