package com.carpathianapiary.beekeepernotes.database.userData

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.carpathianapiary.beekeepernotes.utils.BeekeperApp
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val FB_KEY_USERS = "users"

class UserDataRepository private constructor() : UserDataSource {


    private val userDataCache: MutableLiveData<UserData> = MutableLiveData()

    private val fireStore = FirebaseFirestore.getInstance()

    private val userDataDao = BeekeperApp.getInstance().roomDatabase.userDao()

    init {
        getCurrentUser(null)
    }

    override fun loadUser(firebaseUser: FirebaseUser, token: String, loadUserDataCallback: UserDataSource.LoadUserDataCallback) {
        fireStore.collection(FB_KEY_USERS)
                .document(firebaseUser.uid)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        val user = documentSnapshot.toObject(User::class.java)
                        user!!.phoneToken = token
                        user.type = User.TYPE_ONLINE
                        saveUserData(user.mapToUserData(firebaseUser.uid), loadUserDataCallback)
                    } else {
                        uploadUser(firebaseUser.uid, createNewUser(firebaseUser, token), loadUserDataCallback)
                    }
                }
                .addOnFailureListener { e ->
                    Log.e(TAG, "saveUserInfo onFailure: $e")

                }
    }

    override fun getCurrentUser(loadUserDataCallback: UserDataSource.LoadUserDataCallback?) {
        GlobalScope.launch {
            val user = userDataDao.getUser()
            if (user != null) {
                userDataCache.postValue(user)
                withContext(Dispatchers.Main) { loadUserDataCallback?.onUserLoaded(user) }
            } else {
                withContext(Dispatchers.Main) {loadUserDataCallback?.onUserLoadFailed(Throwable("User not exist yet"))}
            }
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
            userDataDao.deleteUser()
            userDataDao.insertAll(userData)
            userDataCache.postValue(userData)
            withContext(Dispatchers.Main) {loadUserDataCallback.onUserLoaded(userData)}
        }
    }

    override fun getUserData(): LiveData<UserData> {
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
            userDataDao.deleteUser()
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