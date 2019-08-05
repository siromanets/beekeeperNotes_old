package com.example.key.beekeepernote.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil

import com.example.key.beekeepernote.R
import com.example.key.beekeepernote.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.name1Button.setOnClickListener { startNewActivities(it) }
    }

    private fun startNewActivities(view: View) {
        val intent = Intent (this, Beehive::class.java)
        intent.putExtra("button_id",view.id)
        startActivity(intent)
    }
    fun toastMe(view: View) {
        // val myToast = Toast.makeText(this, message, duration);
        val myToast = Toast.makeText(this, "Hello!!!", Toast.LENGTH_SHORT)
        myToast.show()
    }
}
