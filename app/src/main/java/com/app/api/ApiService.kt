package com.app.api

import retrofit2.http.POST

/**
 *
 *   ApiService.kt
 *   Created by ThangTX86 on 07/12/2022
 *
 **/
interface ApiService {

    @POST
    suspend fun postSSID()
}