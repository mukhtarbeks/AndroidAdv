package com.labs.kbtu.lab5.models

import com.google.gson.annotations.SerializedName

class MovieResults{
  @SerializedName("results")
  var movies: List<Movie>? = null
  @SerializedName("page")
  var page: Int = 0
  @SerializedName("total_results")
  private val mTotalResults: Int = 0
  @SerializedName("total_pages")
  private val mTotalPages: Int = 0

}