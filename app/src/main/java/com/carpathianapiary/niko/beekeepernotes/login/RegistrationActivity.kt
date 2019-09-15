package com.carpathianapiary.niko.beekeepernotes.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.carpathianapiary.niko.beekeepernotes.R
import com.carpathianapiary.niko.beekeepernotes.database.userData.User
import com.carpathianapiary.niko.beekeepernotes.database.userData.UserData
import com.carpathianapiary.niko.beekeepernotes.databinding.RegistrationActivityBinding
import com.carpathianapiary.niko.beekeepernotes.utils.CustomProgressDialog
import com.carpathianapiary.niko.beekeepernotes.utils.Resource
import com.carpathianapiary.niko.beekeepernotes.utils.Status
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse

private const val SIGN_IN_RESPONSE_CODE = 123

class RegistrationActivity : AppCompatActivity(), UserTypeSelectorFragment.UserTypeSelectionListener {

    private var progress: CustomProgressDialog? = null

    private lateinit var binding: RegistrationActivityBinding

    private lateinit var viewModel: RegistrationViewModel

    companion object {

        private const val TAG = "RegistrationActivity"

        fun show(context: Context) {
            context.startActivity(Intent(context, RegistrationActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.registration_activity)
        viewModel = ViewModelProviders.of(this).get(RegistrationViewModel::class.java)
        viewModel.getUserData().observe(this, Observer { onUpdateUserData(it) })
        viewModel.getUserType().observe(this, Observer { prepareUserType(it) })
    }

    private fun prepareUserType(userType: Int) {
        when(userType) {
            User.TYPE_OFFLINE -> showLogin()
            User.TYPE_NOT_EXIST -> binding.container.visibility = View.VISIBLE
        }
    }

    private fun onUpdateUserData(resource: Resource<UserData>) {
        when (resource.status) {
            Status.SUCCESS -> {
                cancelProgress()
                resource.data?.let { finish() }
            }

            Status.ERROR -> {
                cancelProgress()
                resource.message?.let {
                    showMessage(it)
                }
            }

            Status.LOADING -> showProgress()
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }


    private fun showProgress() {
        cancelProgress()
        progress = CustomProgressDialog()
        progress?.show(supportFragmentManager, CustomProgressDialog.TAG)
    }

    private fun cancelProgress() {
        if (progress != null) {
            progress?.dismiss()
            progress = null
        }
    }

    private fun showLogin() {
        binding.container.visibility = View.GONE
        val intent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setLogo(R.mipmap.ic_launcher)
                .setTheme(R.style.MyMaterialTheme)
                .setIsSmartLockEnabled(false)
                .setAvailableProviders(listOf(
                        AuthUI.IdpConfig.EmailBuilder().build(),
                        AuthUI.IdpConfig.GoogleBuilder().build()))
                .build()
        startActivityForResult(intent, SIGN_IN_RESPONSE_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SIGN_IN_RESPONSE_CODE) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                viewModel.saveUser()
            } else {
                if (response != null) {
                    Log.d(TAG, "onActivityResult failed: " + response.error!!)
                }
            }
        }
    }

    override fun onSignInSelect() {
        showLogin()
    }

    override fun onAnonymousSelected() {
        viewModel.createAnonymousUser()
    }
}
