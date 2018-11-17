package com.labs.kbtu.lab5.services

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceGenerator {

  val ENDPOINT = "https://api.themoviedb.org/3/"
  val API_KEY = "91ef52ef30eeb3da1f46bad51aeb9d1e"

  private val httpClient = OkHttpClient.Builder()
    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))


  private val builder = Retrofit.Builder()
    .baseUrl(ENDPOINT)
    .addConverterFactory(GsonConverterFactory.create())

  fun <S> createService(serviceClass: Class<S>): S {
    val retrofit = builder.client(httpClient.build()).build()
    return retrofit.create(serviceClass)
  }
}