package com.app

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.util.Log
import java.net.*
import java.util.*
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 *
 *   Extensions.kt
 *   Created by ThangTX86 on 07/12/2022
 *
 **/


fun log(message: String) {
    Log.d("ThangTX2", message)
}

fun Context.checkNetworkInfo(isConnected: (Boolean) -> Unit) {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities == null) {
            isConnected.invoke(false)
        }
        connectivityManager.registerNetworkCallback(
            NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET).build(),
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    isConnected.invoke(true)
                }

                override fun onLost(network: Network) {
                    isConnected.invoke(false)
                }
            })
    } else {
        val networkInfo = connectivityManager.activeNetworkInfo
        isConnected.invoke(networkInfo != null && networkInfo.isConnectedOrConnecting)
    }
}

fun Int.intToIp(): String {
    return ((this and 0xFF).toString() + "."
            + (this shr 8 and 0xFF) + "."
            + (this shr 16 and 0xFF) + "."
            + (this shr 24 and 0xFF))
}

fun getIPv4Address(): String {
    try {
        val enumNetworkInterfaces: Enumeration<NetworkInterface> =
            NetworkInterface.getNetworkInterfaces()
        while (enumNetworkInterfaces.hasMoreElements()) {
            val networkInterface: NetworkInterface = enumNetworkInterfaces.nextElement()
            val enumIpAddr: Enumeration<InetAddress> = networkInterface.getInetAddresses()
            while (enumIpAddr.hasMoreElements()) {
                val inetAddress: InetAddress = enumIpAddr.nextElement()
                if (!inetAddress.isLoopbackAddress && inetAddress is Inet4Address) {
                    return inetAddress.getHostAddress()
                }
            }
        }
    } catch (ex: SocketException) {
        Log.e("getIPv4Address()", ex.toString())
    }
    return "N/A"
}

fun getIPv6Address(): String {
    try {
        val enumNetworkInterfaces = NetworkInterface.getNetworkInterfaces()
        while (enumNetworkInterfaces.hasMoreElements()) {
            val networkInterface = enumNetworkInterfaces.nextElement()
            val enumIpAddr = networkInterface.inetAddresses
            while (enumIpAddr.hasMoreElements()) {
                val inetAddress = enumIpAddr.nextElement()
                if (!inetAddress.isLoopbackAddress && inetAddress is Inet6Address) {
                    return inetAddress.hostAddress
                }
            }
        }
    } catch (ex: SocketException) {
        Log.e("getIPv6Address()", ex.toString())
    }
    return "N/A"
}