package com.sepanta.controlkit.inboxviewkit.view.viewModel.state

import com.sepanta.controlkit.inboxviewkit.service.apiError.ApiError

sealed class InboxViewState {
    object Initial : InboxViewState()
    object NoData : InboxViewState()
    object ShowData : InboxViewState()
    data class Error(val data: ApiError?) : InboxViewState()


}

