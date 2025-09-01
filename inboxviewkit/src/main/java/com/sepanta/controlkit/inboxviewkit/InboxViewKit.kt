package com.sepanta.controlkit.inboxviewkit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sepanta.controlkit.inboxviewkit.config.InboxViewServiceConfig
import com.inboxview.view.config.InboxViewStyle
import com.sepanta.controlkit.inboxviewkit.service.ApiService
import com.sepanta.controlkit.inboxviewkit.service.InboxViewApi
import com.sepanta.controlkit.inboxviewkit.service.RetrofitClientInstance
import com.sepanta.controlkit.inboxviewkit.view.viewModel.InboxViewModel
import com.sepanta.controlkit.inboxviewkit.view.viewModel.InboxViewModelFactory
import com.sepanta.controlkit.inboxviewkit.view.viewModel.state.InboxViewState


class InboxViewKit(
    private var config: InboxViewServiceConfig = InboxViewServiceConfig(),
) {

    @Composable
    fun Configure(onDismiss: (() -> Unit)? = null, onState: ((InboxViewState) -> Unit)? = null) {

        if (RetrofitClientInstance.getRetrofitInstance(
            ) == null
        ) return
        val api = InboxViewApi(
            RetrofitClientInstance.getRetrofitInstance(
                config.timeOut,
                config.maxRetry, config.timeRetryThreadSleep
            )!!.create(ApiService::class.java)
        )
        val viewModel: InboxViewModel = viewModel(
            factory = InboxViewModelFactory(api)
        )
        viewModel.setConfig(config)
        val state = viewModel.state.collectAsState().value

        LaunchedEffect(Unit) {
            viewModel.launchAlertEvent.collect {
                onDismiss?.invoke()
            }
        }


        when (state) {

            InboxViewState.Initial -> onState?.invoke(InboxViewState.Initial)

            InboxViewState.NoData -> {
                onState?.invoke(InboxViewState.NoData)
            }

            is InboxViewState.ShowData -> {
                    InboxViewStyle.checkViewStyle(config.viewConfig.viewStyle)
                        .ShowView(config = config.viewConfig, viewModel)
                viewModel.showDialog()


            }

            is InboxViewState.Error -> {
                onState?.invoke(InboxViewState.Error(state.data))


            }
        }

    }




}


