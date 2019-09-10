package com.carpathianapiary.niko.beekeepernotes.database.userData

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.carpathianapiary.niko.beekeepernotes.utils.BeekeperApp
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

private const val FB_KEY_USERS = "users"

class UserDataRepository private constructor() : UserDataSource {

    private val userDataCache: MutableLiveData<UserData> = MutableLiveData()

    private val fireStore = FirebaseFirestore.getInstance()

    private val userDataDao = BeekeperApp.getInstance().roomDatabase.userDao()

    override fun loadUser(firebaseUser: FirebaseUser, token: String, loadUserDataCallback: UserDataSource.LoadUserDataCallback) {
        fireStore.collection(FB_KEY_USERS)
                .document(firebaseUser.uid)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        val user = documentSnapshot.toObject(User::class.java)
                        user!!.phoneToken = token
                        saveUserData(user.mapToUserData(firebaseUser.uid), loadUserDataCallback)
                    } else {
                        uploadUser(firebaseUser.uid, createNewUser(firebaseUser, token), loadUserDataCallback)
                    }
                }
                .addOnFailureListener { e ->
                    Log.e(TAG, "saveUserInfo onFailure: $e")

                }
    }

    private fun createNewUser(firebaseUser: FirebaseUser, token: String): User {
        return User(
                name = firebaseUser.displayName ?: "",
                email = firebaseUser.email ?: "",
                photoUrl = firebaseUser.photoUrl.toString(),
                phoneNumberOne = firebaseUser.phoneNumber ?: "",
                phoneToken = token,
                type = User.TYPE_ONLINE
        )
    }

    private fun uploadUser(userId: String, user: User, loadUserDataCallback: UserDataSource.LoadUserDataCallback) {
        fireStore.collection(FB_KEY_USERS)
                .document(userId)
                .set(user, SetOptions.merge())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "sendUserInfo onComplete: ")
                        saveUserData(user.mapToUserData(userId), loadUserDataCallback)
                    } else {
                        Log.d(TAG, "sendUserInfo not onComplete: ")
                        loadUserDataCallback.onUserLoadFailed(Throwable(task.result.toString()))
                    }
                }
                .addOnFailureListener { e ->
                    Log.e(TAG, "sendUserInfo onFailure: $e")
                    loadUserDataCallback.onUserLoadFailed(Throwable(e.message))
                }
    }

    override fun saveUserData(userData: UserData, loadUserDataCallback: UserDataSource.LoadUserDataCallback) {
        GlobalScope.launch {
            userDataDao.insertAll(userData)
            userDataCache.postValue(userData)
            loadUserDataCallback.onUserLoaded(userData)
        }
    }

    override fun getUserData(userId: String): LiveData<UserData> {
        return userDataCache
    }

    override fun updateUserData(user: UserData, loadUserDataCallback: UserDataSource.LoadUserDataCallback) {
        fireStore.collection(FB_KEY_USERS)
                .document(user.uid)
                .set(user, SetOptions.merge())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "sendUserInfo onComplete: ")
                        saveUserData(user, loadUserDataCallback)
                    } else {
                        Log.d(TAG, "sendUserInfo not onComplete: ")
                        loadUserDataCallback.onUserLoadFailed(Throwable(task.result.toString()))
                    }
                }
                .addOnFailureListener { e ->
                    Log.e(TAG, "sendUserInfo onFailure: $e")
                    loadUserDataCallback.onUserLoadFailed(Throwable(e.message))
                }
    }

    override fun deleteUserDate(user: UserData) {
        GlobalScope.launch {
            userDataDao.getUserById(user.uid)
            userDataCache.postValue(null)
        }
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