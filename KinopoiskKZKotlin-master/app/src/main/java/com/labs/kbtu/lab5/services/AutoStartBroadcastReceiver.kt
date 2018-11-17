package com.labs.kbtu.lab5.services

import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context
import android.util.Log


class AutoStartBroadcastReceiver : BroadcastReceiver() {
  override fun onReceive(context: Context, arg1: Intent) {
    val intent = Intent(context, InfiniteService::class.java)
    context.startService(intent)
    Log.i("Autostart", "started")
  }
}