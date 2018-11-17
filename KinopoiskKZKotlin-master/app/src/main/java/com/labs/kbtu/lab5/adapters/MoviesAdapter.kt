package com.labs.kbtu.lab5.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.labs.kbtu.lab5.R
import com.labs.kbtu.lab5.models.Movie
import com.labs.kbtu.lab5.utils.Constants
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_movie.view.*
import java.text.FieldPosition
import com.labs.kbtu.lab5.R.id.progressBar
import com.labs.kbtu.lab5.utils.loadImageFrom
import java.lang.Exception


class MoviesAdapter(val movies: List<Movie>): RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {
  lateinit var context: Context
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
    context = parent.context
    return ViewHolder(view)
  }

  override fun getItemCount(): Int {
    return movies.size
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val movie = movies[position]
    with(holder) {

      view.nameTextView.text = movie.title
      view.originalNameTextView.text = movie.originalTitle
      view.releaseTextView.text = "${movie.releaseDate?.substring(0, 4)}, ${movie.voteAverage}"
      view.imageProgressBar.visibility = View.VISIBLE

      view.movieImageView.loadImageFrom(Constants.TMDB_IMAGE_URL + Constants.POSTER_SIZE_W342 + movie.posterPath,
        object : Callback{
          override fun onSuccess() { view.imageProgressBar.visibility = View.GONE }
          override fun onError(e: Exception?) {
            view.movieImageView.setBackgroundResource(R.drawable.placeholder)
            view.imageProgressBar.visibility = View.GONE
          }
        })
    }
  }

  class ViewHolder(val view: View): RecyclerView.ViewHolder(view)
}