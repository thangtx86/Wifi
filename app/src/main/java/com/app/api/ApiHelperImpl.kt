package com.app.api

/**
 *
 *   ApiHelperImpl.kt
 *   Created by ThangTX86 on 07/12/2022
 *
 **/
class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {
    override suspend fun postSSID() = apiService.postSSID()
}