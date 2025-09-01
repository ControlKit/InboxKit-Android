package com.sepanta.controlkit.inboxviewkit.config

import com.inboxview.view.config.InboxViewConfig


data class InboxViewServiceConfig(
    var viewConfig: InboxViewConfig = InboxViewConfig(),
    var route: String = "http://135.181.38.185:8001/api/force-updates",
    var packageName: String? = null,
    var version: String = "0.0.0",
    var appId: String = "Android",
    var deviceId: String="1",
    var timeOut: Long = 2000L,
    var timeRetryThreadSleep: Long = 2000L,
    var maxRetry: Int = 3)