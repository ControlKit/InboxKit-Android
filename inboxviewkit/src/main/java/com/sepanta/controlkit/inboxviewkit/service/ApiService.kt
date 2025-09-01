package com.sepanta.controlkit.inboxviewkit.service

import com.sepanta.controlkit.inboxviewkit.service.model.ApiCheckUpdateResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Url

interface ApiService {

    @GET()
    suspend fun getData(
        @Url url: String,
        @Header("x-app-id") appId: String?,
        @Header("x-version") version: String,
        @Header("x-device-uuid") deviceId: String?,
    ): Response<ApiCheckUpdateResponse>
}