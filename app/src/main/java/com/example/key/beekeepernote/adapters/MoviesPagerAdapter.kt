package com.example.key.beekeepernote.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.key.beekeepernote.fragments.Movie
import com.example.key.beekeepernote.fragments.MovieFragment

// 1
private const val MAX_VALUE = 200
class MoviesPagerAdapter(fragmentManager: FragmentManager, private val movies: List<Movie>) :
        FragmentStatePagerAdapter(fragmentManager) {

    override fun getPageTitle(position: Int): CharSequence {
        return movies[position % movies.size].title
    }

    // 2
    override fun getItem(position: Int): Fragment {
        return MovieFragment.newInstance(movies[position % movies.size])
    }

    // 3
    override fun getCount(): Int {
        return movies.size * MAX_VALUE
    }
}