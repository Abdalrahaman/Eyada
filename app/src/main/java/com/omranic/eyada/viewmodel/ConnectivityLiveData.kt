package com.omranic.eyada.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import androidx.lifecycle.LiveData
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ConnectivityLiveData @Inject constructor(@ApplicationContext context: Context) : LiveData<Boolean>() {
    private lateinit var networkCallback: ConnectivityManager.NetworkCallback
    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val validNetworks: MutableSet<Network> = HashSet()
    private val TAG = "ConnectivityLiveData"

    init {
        checkValidNetworks()
    }

    private fun checkValidNetworks(){
        postValue(validNetworks.isNotEmpty())
        Log.d(TAG, "checkValidNetworks: ")
    }

     override fun onActive() {
        super.onActive()
         networkCallback = createNetworkCallBack()
         val networkRequest = NetworkRequest.Builder()
             .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
             .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
             .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
             .build()
         connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
         Log.d(TAG, "onActive: ")
     }

    override fun onInactive() {
        super.onInactive()
        connectivityManager.unregisterNetworkCallback(networkCallback)
        Log.d(TAG, "onInactive: ")
    }

    private fun createNetworkCallBack() = object : ConnectivityManager.NetworkCallback(){
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            val isInternet = connectivityManager.getNetworkCapabilities(network)?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            if (isInternet == true){
                validNetworks.add(network)
            }
            checkValidNetworks()
            Log.d(TAG, "onAvailable: ")
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            validNetworks.remove(network)
            checkValidNetworks()
            Log.d(TAG, "onLost: ")
        }
    }
}