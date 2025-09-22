package com.sepanta.controlkit.inboxviewkit

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sepanta.controlkit.inboxviewkit.config.InboxViewServiceConfig
import com.inboxview.view.config.InboxViewStyle
import com.sepanta.controlkit.inboxviewkit.service.ApiService
import com.sepanta.controlkit.inboxviewkit.service.InboxViewApi
import com.sepanta.controlkit.inboxviewkit.service.RetrofitClientInstance
import com.sepanta.controlkit.inboxviewkit.service.local.LocalDataSource
import com.sepanta.controlkit.inboxviewkit.utils.UniqueUserIdProvider
import com.sepanta.controlkit.inboxviewkit.view.viewModel.InboxViewModel
import com.sepanta.controlkit.inboxviewkit.view.viewModel.InboxViewModelFactory
import com.sepanta.controlkit.inboxviewkit.view.viewModel.state.InboxViewState


class InboxViewKit(
    private var config: InboxViewServiceConfig,
    context: Context? = null,

    ) {

    private var _viewModel: InboxViewModel? = null
    val viewModel: InboxViewModel
        get() = _viewModel ?: throw kotlin.IllegalStateException("ViewModel not initialized yet")

    init {

        context?.let { setupViewModel(it) }
    }

    private fun setupViewModel(context: Context) {
        val retrofit = RetrofitClientInstance.getRetrofitInstance(
            config.timeOut,
            config.maxRetry,
            config.timeRetryThreadSleep
        ) ?: return

        val localDataSource = LocalDataSource(context)
        val api = InboxViewApi(retrofit.create(ApiService::class.java))
        _viewModel = InboxViewModelFactory(
            api,
            localDataSource
        ).create(InboxViewModel::class.java)

        if (config.deviceId == null) {
            config.deviceId = UniqueUserIdProvider.getOrCreateUserId(context)
            _viewModel?.setConfig(config)

        } else {
            _viewModel?.setConfig(config)

        }
    }


    @Composable
    internal fun ConfigureComposable(
        onDismiss: (() -> Unit)? = null,
        onState: ((InboxViewState) -> Unit)? = null,
    ) {
        if (_viewModel == null) return
        val state = _viewModel?.state?.collectAsState()?.value

        LaunchedEffect(Unit) {
            _viewModel?.launchAlertEvent?.collect {
                onDismiss?.invoke()
            }
        }
        InitView()

        when (state) {

            InboxViewState.Initial -> onState?.invoke(InboxViewState.Initial)

            InboxViewState.NoData -> {
                onState?.invoke(InboxViewState.NoData)
            }

            is InboxViewState.ShowData -> {
                _viewModel?.showDialog()

            }

            is InboxViewState.Error -> {
                onState?.invoke(InboxViewState.Error(state.data))


            }

            else -> Unit
        }
    }

    fun showView() {
        _viewModel?.getData()
    }

    @Composable
    private fun InitView() {
        InboxViewStyle.checkViewStyle(config.viewConfig.viewStyle)
            .ShowView(config = config.viewConfig, viewModel)

    }
}

@Composable
fun inboxViewKitHost(
    config: InboxViewServiceConfig,
    onDismiss: (() -> Unit)? = null,
    onState: ((InboxViewState) -> Unit)? = null,
): InboxViewKit {
    val context = LocalContext.current

    val kit = remember { InboxViewKit(config, context = context) }
    kit.ConfigureComposable(onDismiss = onDismiss, onState = onState)
    return kit
}

