package com.sepanta.controlkit.inboxviewkit.service

import com.sepanta.controlkit.inboxviewkit.service.apiError.NetworkResult
import com.sepanta.controlkit.inboxviewkit.service.apiError.handleApi
import com.sepanta.controlkit.inboxviewkit.service.model.ActionResponse
import com.sepanta.controlkit.inboxviewkit.service.model.ApiCheckUpdateResponse


class InboxViewApi(private val apiService: ApiService) {

    suspend fun getInboxViewData(
        route: String,
        appId: String,
        version: String,
        deviceId: String,
        sdkVersion: String,
    ): NetworkResult<ApiCheckUpdateResponse> {
        return handleApi {
            apiService.getData(route, appId, version, deviceId,sdkVersion)
        }
    }
    suspend fun setAction(
        route: String,
        appId: String,
        version: String,
        deviceId: String,
        sdkVersion: String,
        action: String,
    ): NetworkResult<ActionResponse> {
        return handleApi {
            apiService.setAction(
                url = route,
                appId = appId,
                version = version,
                deviceId = deviceId,
                sdkVersion = sdkVersion,
                action = action,
            )
        }
    }
}
