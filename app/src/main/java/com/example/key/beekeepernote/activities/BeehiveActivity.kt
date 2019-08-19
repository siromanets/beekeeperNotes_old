package com.example.key.beekeepernote.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.key.beekeepernote.R

class BeehiveActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beehive)
        val buttonId = intent.getIntExtra("button_id",0)
    }
}
