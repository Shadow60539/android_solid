package com.example.solid.data.core

import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject

interface InternetConnection {
    fun isConnected(): Boolean
}


class InternetConnectionImpl @Inject constructor(private val context: Context) :
    InternetConnection {

    override fun isConnected(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null
    }
}