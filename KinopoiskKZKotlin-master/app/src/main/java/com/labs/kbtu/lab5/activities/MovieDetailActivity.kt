package com.labs.kbtu.lab5.activities

import android.opengl.Visibility
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.labs.kbtu.lab5.R
import com.labs.kbtu.lab5.models.Movie
import com.labs.kbtu.lab5.utils.Constants
import com.labs.kbtu.lab5.utils.loadImageFrom
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_detail.*
import java.lang.Exception

class MovieDetailActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_movie_detail)

    val movie = intent.getParcelableExtra<Movie>(getString(R.string.clicked_movie))

    nameTextView.text = movie.title
    overviewTextView.text = movie.overview

    movieImageView.loadImageFrom(Constants.TMDB_IMAGE_URL + Constants.BACKDROP_SIZE_W780 + movie.backdropPath,
      object : Callback{
        override fun onSuccess() { progressBar.visibility = View.GONE }
        override fun onError(e: Exception?) {
          progressBar.visibility = View.GONE
          movieImageView.setBackgroundResource(R.drawable.placeholder)
        }
      })

    supportActionBar?.setDisplayHomeAsUpEnabled(true)

  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    val id = item?.itemId
    if (id == android.R.id.home) {
      finish()
    }
    return super.onOptionsItemSelected(item)
  }
}
