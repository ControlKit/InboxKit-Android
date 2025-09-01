package com.sepanta.controlkit.inboxviewkit.view.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inboxview.config.InboxViewServiceConfig
import com.sepanta.controlkit.inboxviewkit.service.model.InboxViewResponse
import com.sepanta.controlkit.inboxviewkit.service.model.toDomainList
import com.sepanta.controlkit.inboxviewkit.service.InboxViewApi
import com.sepanta.controlkit.inboxviewkit.service.apiError.NetworkResult
import com.sepanta.controlkit.inboxviewkit.view.viewModel.state.InboxViewState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class InboxViewModel(
    private val api: InboxViewApi,
) : ViewModel() {
    private val _dataList = MutableStateFlow<List<InboxViewResponse>>(emptyList())
    val dataList: StateFlow<List<InboxViewResponse>> = _dataList


    private var config: InboxViewServiceConfig? = null
    fun setConfig(config: InboxViewServiceConfig) {
        this.config = config
        getData()
    }
    private val _mutableState = MutableStateFlow<InboxViewState>(InboxViewState.Initial)
    val state: StateFlow<InboxViewState> = _mutableState.asStateFlow()

    private val _currentIndex = MutableStateFlow(0)
    val currentIndex = _currentIndex.asStateFlow()

    private val _currentModel = MutableStateFlow(InboxViewResponse())
    val currentModel = _currentModel.asStateFlow()

    private val _showDetailPage = MutableStateFlow(false)
    val showDetailPage = _showDetailPage.asStateFlow()

    private val _nextButtonState = MutableStateFlow(true)
    val nextButtonState = _nextButtonState.asStateFlow()

    private val _previousButtonState = MutableStateFlow(true)
    val previousButtonState = _previousButtonState.asStateFlow()




    fun setShowDetailPage(boolean: Boolean) {
        _showDetailPage.value = boolean
    }

    private fun setButtonsState(index: Int) {
        _nextButtonState.value = index != dataList.value.size - 1
        _previousButtonState.value = index != 0
    }

    fun setCurrentIndex(index: Int) {
        _currentIndex.value = index
        _currentModel.value = dataList.value[index]
        setButtonsState(index)
    }

    fun next() {
        if (!_nextButtonState.value) return
        _currentIndex.value = _currentIndex.value + 1
        setCurrentIndex(_currentIndex.value)
    }

    fun previous() {
        if (!_previousButtonState.value) return
        _currentIndex.value = _currentIndex.value - 1
        setCurrentIndex(_currentIndex.value)
    }



    fun getData() {
        if (state.value != InboxViewState.Initial || config == null) return
        viewModelScope.launch {
            val data = api.getInboxViewData(
                config!!.route,
                config!!.appId,
                config!!.version,
                config!!.deviceId,
            )


            when (data) {
                is NetworkResult.Success -> {

                    if (data.value != null) {
                        _dataList.value = data.value.toDomainList()
                        _dataList.value = _dataList.value+data.value.toDomainList()
                        _dataList.value = _dataList.value+data.value.toDomainList()
                        _mutableState.value = InboxViewState.ShowData

                    } else {
                        _mutableState.value = InboxViewState.NoData

                    }
                }

                is NetworkResult.Error -> {

                    _mutableState.value = InboxViewState.Error(data.error)
                }
            }
        }


    }
    private val _openDialog = MutableStateFlow(true)
    val openDialog: StateFlow<Boolean> = _openDialog.asStateFlow()

    fun submitDialog() {
        _openDialog.value = false
        clearState()
    }

    fun dismissDialog() {
        _openDialog.value = false
        triggerLaunchAlert()
        clearState()

    }
    fun clearState() {
        _mutableState.value = InboxViewState.Initial
    }

    private val _launchAlertEvent = Channel<Unit>(Channel.BUFFERED)
    val launchAlertEvent = _launchAlertEvent.receiveAsFlow()

    fun triggerLaunchAlert() {
        viewModelScope.launch {
            _launchAlertEvent.send(Unit)
        }
    }
}