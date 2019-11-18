package com.carpathianapiary.beekeepernotes.apiary

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.carpathianapiary.beekeepernotes.R

class ApiaryActivity : AppCompatActivity() {

    companion object {

        private const val TAG = "Apiary"

        fun show(context: Context) {
            context.startActivity(Intent(context, ApiaryActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.apiary_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, ApiaryFragment.newInstance())
                    .commitNow()
        }
    }

}
