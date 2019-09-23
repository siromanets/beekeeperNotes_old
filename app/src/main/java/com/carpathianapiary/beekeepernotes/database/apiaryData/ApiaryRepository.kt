package com.carpathianapiary.beekeepernotes.database.apiaryData

import android.util.Log
import androidx.lifecycle.LiveData
import com.carpathianapiary.beekeepernotes.database.userData.User
import com.carpathianapiary.beekeepernotes.database.userData.UserData
import com.carpathianapiary.beekeepernotes.database.userData.UserDataRepository
import com.carpathianapiary.beekeepernotes.database.userData.UserDataSource
import com.carpathianapiary.beekeepernotes.utils.BeekeperApp
import com.carpathianapiary.beekeepernotes.utils.LogUtils
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ApiaryRepository : ApiaryDataSource {
    
    private val userDataRepository = UserDataRepository.instance
    
    private val apiaryDao = BeekeperApp.getInstance().roomDatabase.apiaryDao()
    
    private val fireStore = FirebaseFirestore.getInstance()
    
    lateinit var user: UserData
    
    init {
        userDataRepository.getCurrentUser(object : UserDataSource.LoadUserDataCallback {
            override fun onUserLoaded(userData: UserData) {
                user = userData
            }
            
            override fun onUserLoadFailed(throwable: Throwable) {
                LogUtils.e(TAG, "onUserLoadFailed ;" + throwable.message)
            }
            
        })
    }
    
    override fun getApiariesData(): LiveData<List<ApiaryData>> {
        return apiaryDao.getApiariesData()
    }
    
    override fun getApiaryById(id: String): LiveData<ApiaryData> {
        return apiaryDao.getApiaryById(id)
    }
    
    override fun uploadApiary(apiaryData: ApiaryData, callback: ApiaryDataSource.UploadApiaryCallback) {
        if (user.type == User.TYPE_ONLINE) {
            fireStore.collection(FB_KEY_APIARIES)
                    .add(apiaryData.mapToApiary(user.uid))
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, "uploadApiary onComplete: ")
                            GlobalScope.launch {
                                apiaryDao.insertAll(apiaryData)
                            }
                            callback.onApiaryUploaded(apiaryData)
                        } else {
                            Log.d(TAG, "uploadApiary not onComplete: ")
                            callback.onApiaryUploadFailed(Throwable(task.result.toString()))
                        }
                    }
                    .addOnFailureListener { e ->
                        Log.e(TAG, "sendUserInfo onFailure: $e")
                        callback.onApiaryUploadFailed(Throwable(e.message))
                    }
        } else {
            apiaryDao.insertAll(apiaryData)
            callback.onApiaryUploaded(apiaryData)
        }
        
    }
    
    override fun updateApiaries(callback: ApiaryDataSource.UpdateApiaryCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    
    companion object {
        
        private const val TAG = "ApiaryRepository"
        
        private const val FB_KEY_APIARIES = "apiaries"
        
        private var INSTANCE: ApiaryRepository? = null
        
        val instance: ApiaryRepository
            get() {
                if (INSTANCE == null) {
                    synchronized(ApiaryRepository::class.java) {
                        if (INSTANCE == null) {
                            INSTANCE = ApiaryRepository()
                        }
                    }
                }
                return this.INSTANCE!!
            }
    }
}