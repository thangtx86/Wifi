package com.app

/**
 *
 *   WifiInfo.kt
 *   Created by ThangTX86 on 08/12/2022
 *
 **/
data class WifiInfo(
    val deviceId: String,
    val date: String,
    val ssid: String,
    val bssid: String,
    val ipv4: String,
    val ipv6: String,
    val gatewayIP: String,
    val hostName: String,
    val dsn1: String,
    val dsn2: String,
    val subnetMark: String,
    val networkId: Int,
)