package com.app

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import androidx.core.app.ActivityCompat


/**
 *
 *   NetworkStateReceiver.kt
 *   Created by ThangTX86 on 07/12/2022
 *
 **/
class NetworkStateReceiver(private val context: Context) : BroadcastReceiver() {

    private var mManager: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private var mWifiManager: WifiManager =
        context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
    private var mListeners: MutableList<NetworkStateReceiverListener> = ArrayList()
    private var mConnected: Boolean = false

    override fun onReceive(context: Context, intent: Intent?) {
        if (intent == null || intent.extras == null)
            return
        if (checkStateChanged()) notifyStateToAll()
    }

    private fun checkStateChanged(): Boolean {
        val prev = mConnected
        val activeNetwork = mManager.activeNetworkInfo
        mConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting
        return prev != mConnected
    }


    private fun notifyStateToAll() {
        for (listener in mListeners) {
            notifyState(listener)
        }
    }

    private fun notifyState(listener: NetworkStateReceiverListener?) {
        if (listener != null) {
            val wifiInfo = mWifiManager.connectionInfo
            if (mConnected) {

                listener.onNetworkAvailable(wifiInfo.ssid)
            } else {
                listener.onNetworkUnavailable(wifiInfo.ssid)
            }
        }
    }

    //call this method to add a listener
    fun addListener(listener: NetworkStateReceiverListener) {
        mListeners.add(listener)
        notifyState(listener)
    }

    //call this method to remove a listener
    fun removeListener(l: NetworkStateReceiverListener) {
        mListeners.remove(l)
    }

    interface NetworkStateReceiverListener {
        fun onNetworkAvailable(data: String?)

        fun onNetworkUnavailable(data: String?)
    }

    init {
        val intentFilter = IntentFilter()
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        context.registerReceiver(this, intentFilter)
        checkStateChanged()
    }

}