    package com.sepanta.controlkit.inboxviewkit.view.viewModel

    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.ViewModelProvider
    import com.sepanta.controlkit.inboxviewkit.service.InboxViewApi

    class InboxViewModelFactory(
        private val api: InboxViewApi,

    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(InboxViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return InboxViewModel(api) as T
            }
            throw kotlin.IllegalArgumentException("Unknown ViewModel class")
        }
    }