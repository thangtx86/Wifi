package com.app.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 *
 *   RetrofitBuilder.kt
 *   Created by ThangTX86 on 07/12/2022
 *
 **/
object RetrofitBuilder {
    private const val BASE_URL = "http://192.168.1.2:3000/"

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ApiService = getRetrofit().create(ApiService::class.java)
}