package com.carpathianapiary.niko.beekeepernotes

import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.carpathianapiary.niko.beekeepernotes.login.RegistrationActivity
import com.carpathianapiary.niko.beekeepernotes.utils.CustomProgressDialog
import com.carpathianapiary.niko.beekeepernotes.utils.LogUtils

abstract class BaseFragment<V : BaseViewModel> : Fragment() {

    private var progress:CustomProgressDialog? = null

    lateinit var viewModel: V
        private set

    abstract val viewModelClass: Class<V>

    override fun onCreate(savedInstanceState: Bundle?) {
        LogUtils.d(TAG, "onCreateBaseFragment: ")
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(viewModelClass)
        viewModel.invalidUserDataLiveData.observe(this, Observer<Throwable> {
            showActivityLogin()
        })
    }

    fun showProgress() {
        cancelProgress()
        progress = CustomProgressDialog()
        progress?.show(childFragmentManager, CustomProgressDialog.TAG)
    }

    fun cancelProgress() {
        if (progress != null) {
            progress?.dismiss()
            progress = null
        }
    }

    private fun showActivityLogin() {
        RegistrationActivity.show(requireContext())
    }

    companion object {
        private const val TAG = "BaseFragment"
    }
}