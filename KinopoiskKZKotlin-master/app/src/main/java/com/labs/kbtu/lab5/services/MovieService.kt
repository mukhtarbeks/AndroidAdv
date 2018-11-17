package com.labs.kbtu.lab5.services

import com.labs.kbtu.lab5.models.MovieResults
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

  @GET("movie/popular")
  fun popular(
    @Query("api_key") api_key: String,
    @Query("language") lang: String,
    @Query("page") page: Int
  ): Call<MovieResults>


}
