    package com.sepanta.controlkit.inboxviewkit.view.viewModel

    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.ViewModelProvider
    import com.sepanta.controlkit.inboxviewkit.service.InboxViewApi
    import com.sepanta.controlkit.inboxviewkit.service.local.LocalDataSource

    class InboxViewModelFactory(
        private val api: InboxViewApi,
        private val localDataSource: LocalDataSource

    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(InboxViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return InboxViewModel(api,localDataSource) as T
            }
            throw kotlin.IllegalArgumentException("Unknown ViewModel class")
        }
    }