package com.inboxview.view.config

import androidx.compose.runtime.Composable
import com.sepanta.controlkit.inboxviewkit.view.config.InboxViewConfig
import com.sepanta.controlkit.inboxviewkit.view.viewModel.InboxViewModel

interface InboxContract {
    @Composable
    fun ShowView(
        config: InboxViewConfig,
        viewModel: InboxViewModel
    )
}