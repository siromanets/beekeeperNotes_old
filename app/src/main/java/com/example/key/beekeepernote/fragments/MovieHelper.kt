package com.example.key.beekeepernote.fragments

import android.content.Context
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

object MovieHelper {

  val KEY_TITLE = "title"
  val KEY_RATING = "rating"
  val KEY_POSTER_URI = "posterUri"
  val KEY_OVERVIEW = "overview"

  fun getMoviesFromJson(fileName: String, context: Context): ArrayList<Movie> {

    val movies = ArrayList<Movie>()

    try {
      // Load the JSONArray from the file
      val jsonString = loadJsonFromFile(fileName, context)
      val json = JSONObject(jsonString)
      val jsonMovies = json.getJSONArray("movies")

      // Create the list of Movies
      for (index in 0 until jsonMovies.length()) {
        val movieTitle = jsonMovies.getJSONObject(index).getString(KEY_TITLE)
        val movieRating = jsonMovies.getJSONObject(index).getInt(KEY_RATING)
        val moviePosterUri = jsonMovies.getJSONObject(index).getString(KEY_POSTER_URI)
        val movieOverview = jsonMovies.getJSONObject(index).getString(KEY_OVERVIEW)
        movies.add(Movie(movieTitle, movieRating, moviePosterUri, movieOverview))
      }
    } catch (e: JSONException) {
      return movies
    }

    return movies
  }

  private fun loadJsonFromFile(filename: String, context: Context): String {
    var json = ""

    try {
      val input = context.assets.open(filename)
      val size = input.available()
      val buffer = ByteArray(size)
      input.read(buffer)
      input.close()
      json = buffer.toString(Charsets.UTF_8)
    } catch (e: IOException) {
      e.printStackTrace()
    }

    return json
  }
}