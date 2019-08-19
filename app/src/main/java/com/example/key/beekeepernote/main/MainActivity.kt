package com.example.key.beekeepernote.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.view.View

import com.example.key.beekeepernote.R
import com.example.key.beekeepernote.activities.BeehiveActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun startNewActivities(view: View) {
        val intent = Intent(this, BeehiveActivity::class.java)
        intent.putExtra("button_id", view.id)
        startActivity(intent)
    }
}
