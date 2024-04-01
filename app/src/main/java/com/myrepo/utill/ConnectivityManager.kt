package com.myrepo.utill

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build

object ConnectionManager {
    private var network: Network? = null
    private var networkCapability: NetworkCapabilities? = null
    private var connectivityManager: ConnectivityManager? = null

    fun isInternetConnectivityAvailable(context: Context) : Boolean{
        connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val isConnected: Boolean = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            network = connectivityManager?.activeNetwork
            networkCapability = connectivityManager?.getNetworkCapabilities(network)

            networkCapability?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                ?: false
        } else {
            @Suppress("DEPRECATION")
            connectivityManager?.activeNetworkInfo?.isConnectedOrConnecting == true
        }

        return isConnected
    }

}