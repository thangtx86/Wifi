package com.app

import android.content.Context
import android.content.SharedPreferences

/**
 *
 *   NetworkLocal.kt
 *   Created by ThangTX86 on 07/12/2022
 *
 **/

private const val name = "SSID_KEY"
private const val key = "SSID_NAME"

fun setSSID(context: Context, name: String) {
    val sharedPrefs = getPreferences(context)
    sharedPrefs.edit().let {
        it.putString(key, name)
        it.apply()
    }
}

fun getSSID(context: Context): String {
    val sharedPrefs = getPreferences(context)
    val value = sharedPrefs.getString(key, "")
    return value ?: ""
}

private fun getPreferences(context: Context): SharedPreferences {
    return context.getSharedPreferences(name, 0)
}