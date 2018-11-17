package com.labs.kbtu.lab5.services

import android.app.Service
import android.widget.Toast
import android.support.v4.content.ContextCompat.startActivity
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.labs.kbtu.lab5.activities.MoviesActivity


class InfiniteService : Service() {
  override fun onBind(intent: Intent): IBinder? {
    return null
  }

  override fun onDestroy() {
    Toast.makeText(this, "My Service Stopped", Toast.LENGTH_LONG).show()
    Log.d(TAG, "onDestroy")
  }

  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    val intents = Intent(baseContext, MoviesActivity::class.java)
    intents.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    startActivity(intents)

    while (true) {
      Toast.makeText(this, "My Service Started", Toast.LENGTH_LONG).show()
    }

    Log.d(TAG, "onStart")

    return super.onStartCommand(intent, flags, startId)
  }


  companion object {
    private val TAG = "MyService"
  }
}