package com.carpathianapiary.niko.beekeepernotes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

open class BaseViewModel(application: Application) : AndroidViewModel(application) {

    val invalidUserDataLiveData = MutableLiveData<Throwable>()

}
