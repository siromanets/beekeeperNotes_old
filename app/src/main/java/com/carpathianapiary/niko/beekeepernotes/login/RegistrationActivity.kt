package com.carpathianapiary.niko.beekeepernotes.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import com.carpathianapiary.niko.beekeepernotes.R
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse


class RegistrationActivity : AppCompatActivity() {

    private val viewModel: RegistrationViewModel by lazy { ViewModelProviders.of(this).get(RegistrationViewModel::class.java) }

    companion object {

        private const val TAG = "RegistrationActivity"

        fun show(context: Context) {
            context.startActivity(Intent(context, RegistrationActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registration_activity)
        showLogin(123)
    }

    private fun showLogin(requestCode: Int) {
        val intent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setLogo(R.mipmap.ic_launcher)
                .setTheme(R.style.MyMaterialTheme)
                .setIsSmartLockEnabled(false)
                .setAvailableProviders(listOf(
                        AuthUI.IdpConfig.EmailBuilder().build(),
                        AuthUI.IdpConfig.GoogleBuilder().build()))
                .build()
        startActivityForResult(intent, requestCode)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 123) {
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
}
