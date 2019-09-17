package com.carpathianapiary.beekeepernotes.database.apiaryData

import androidx.lifecycle.LiveData

interface ApiaryDataSource {
    
    fun getApiariesData(): LiveData<List<ApiaryData>>
    
    fun getApiaryById(id: String): LiveData<ApiaryData>
    
    fun uploadApiary(apiaryData: ApiaryData, callback: UploadApiaryCallback)
    
    fun updateApiaries(callback: UpdateApiaryCallback)
    
    interface UploadApiaryCallback{
        
        fun onApiaryUploaded(apiaryData: ApiaryData)
        
        fun onApiaryUploadFailed(throwable: Throwable)
    }
    interface UpdateApiaryCallback{
        
        fun onApiaryUpdated(apiaryData: ApiaryData)
        
        fun onApiaryUpdateFailed(throwable: Throwable)
    }
}
