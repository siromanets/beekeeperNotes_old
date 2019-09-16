package com.carpathianapiary.beekeepernotes.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.carpathianapiary.beekeepernotes.R

class BeehiveActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beehive)
        val buttonId = intent.getIntExtra("button_id",0)
    }
}
