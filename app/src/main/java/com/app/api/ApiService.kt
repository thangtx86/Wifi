package com.app.api

import retrofit2.http.GET

/**
 *
 *   ApiService.kt
 *   Created by ThangTX86 on 07/12/2022
 *
 **/
interface ApiService {

    @GET("/login")
    suspend fun postSSID()
}