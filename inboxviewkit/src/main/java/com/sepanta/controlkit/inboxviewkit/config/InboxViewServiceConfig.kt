package com.sepanta.controlkit.inboxviewkit.config

import com.sepanta.controlkit.inboxviewkit.view.config.InboxViewConfig


data class InboxViewServiceConfig(
    var viewConfig: InboxViewConfig = InboxViewConfig(),
    var version: String = "0.0.0",
    var appId: String,
    var deviceId: String? = null,
    var lang: String = "en",
    var timeOut: Long = 5000L,
    var timeRetryThreadSleep: Long = 1000L,
    var maxRetry: Int = 5,
)