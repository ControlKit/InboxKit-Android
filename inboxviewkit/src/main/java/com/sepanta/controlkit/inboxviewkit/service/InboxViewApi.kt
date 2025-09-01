package com.sepanta.controlkit.inboxviewkit.service

import com.sepanta.controlkit.inboxviewkit.service.apiError.NetworkResult
import com.sepanta.controlkit.inboxviewkit.service.apiError.handleApi
import com.sepanta.controlkit.inboxviewkit.service.model.ApiCheckUpdateResponse


class InboxViewApi(private val apiService: ApiService) {

    suspend fun getInboxViewData(
        route: String,
        appId: String,
        version: String,
        deviceId: String,
    ): NetworkResult<ApiCheckUpdateResponse> {
        return handleApi {
            apiService.getData(route, appId, version, deviceId)
        }
    }
}
