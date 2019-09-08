package com.carpathianapiary.niko.beekeepernotes.database.userData

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

private const val FB_KEY_USERS = "users"

class UserDataRepository private constructor() : UserDataSource {

    val userData: MutableLiveData<UserData> = MutableLiveData()

    val fireStore = FirebaseFirestore.getInstance()

    override fun loadUser(userId: String, token: String) {
        fireStore.collection(FB_KEY_USERS)
                .document(userId)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        val user = documentSnapshot.toObject(User::class.java)
                        user!!.phoneToken = token
                        saveUserData(user.mapToUserData(userId))
                        uploadUser(userId, user)
                    } else {

                    }
                }
                .addOnFailureListener { e ->
                    Log.e(TAG, "saveUserInfo onFailure: $e")
                }
    }

    private fun uploadUser(userId: String, user: User) {
        fireStore.collection(FB_KEY_USERS)
                .document(userId)
                .set(user, SetOptions.merge())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "sendUserInfo onComplete: ")
                    } else {
                        Log.d(TAG, "sendUserInfo not onComplete: ")
                    }
                }
                .addOnFailureListener { e -> Log.e(TAG, "sendUserInfo onFailure: $e") }
    }

    val firebaseFireStore = FirebaseFirestore.getInstance()

    override fun saveUserData(userData: UserData) {

    }

    override fun getUserData(userId: String): LiveData<User> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateUserData(user: User) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteUserDate(user: User) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {

        private val TAG = UserDataRepository::class.qualifiedName

        private var INSTANCE: UserDataRepository? = null

        val instance: UserDataRepository
            get() {
                if (INSTANCE == null) {
                    synchronized(UserDataRepository::class.java) {
                        if (INSTANCE == null) {
                            INSTANCE = UserDataRepository()
                        }
                    }
                }
                return this.INSTANCE!!
            }
    }
}