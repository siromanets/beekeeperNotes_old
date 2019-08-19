package com.example.key.beekeepernote.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.example.key.beekeepernote.adapters.MoviesPagerAdapter



import com.example.key.beekeepernote.R
import com.example.key.beekeepernote.activities.BeehiveActivity
import com.example.key.beekeepernote.fragments.Movie
import com.nshmura.recyclertablayout.RecyclerTabLayout

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerTabLayout: RecyclerTabLayout
    private lateinit var viewPager: ViewPager
    private lateinit var pagerAdapter: MoviesPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.viewPager)
        val movies = mutableListOf<Movie>()
        movies.add(Movie("Mesnyky", 3, "", "Sometext"))
        movies.add(Movie("Mesnyky2", 3, "", "Sometext"))
        movies.add(Movie("Mesnyky3", 3, "", "Sometext"))
        pagerAdapter = MoviesPagerAdapter(supportFragmentManager, movies)
        viewPager.adapter = pagerAdapter
        viewPager.currentItem = pagerAdapter.count / 2
        recyclerTabLayout = findViewById(R.id.recyclerTabLayout)
        recyclerTabLayout.setUpWithViewPager(viewPager)

    }


    private fun startNewActivities(view: View) {
        val intent = Intent(this, BeehiveActivity::class.java)
        intent.putExtra("button_id", view.id)
        startActivity(intent)

    }
}
