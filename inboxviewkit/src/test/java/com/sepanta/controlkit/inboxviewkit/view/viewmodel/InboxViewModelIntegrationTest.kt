package com.sepanta.controlkit.inboxviewkit.view.viewmodel

/*
 *  File: InboxViewModelIntegrationTest.kt
 *  Created by morteza on 9/1/25.
 */

import com.sepanta.controlkit.inboxviewkit.config.InboxViewServiceConfig
import com.sepanta.controlkit.inboxviewkit.service.ApiService
import com.sepanta.controlkit.inboxviewkit.service.InboxViewApi
import com.sepanta.controlkit.inboxviewkit.service.model.ApiCheckUpdateResponse
import com.sepanta.controlkit.inboxviewkit.service.model.ApiData
import com.sepanta.controlkit.inboxviewkit.view.viewModel.InboxViewModel
import com.sepanta.controlkit.inboxviewkit.view.viewModel.state.InboxViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.io.IOException

class FakeApiServiceSuccess(val body: ApiCheckUpdateResponse?) : ApiService {
    override suspend fun getData(
        url: String,
        appId: String?,
        version: String,
        deviceId: String?
    ): Response<ApiCheckUpdateResponse> {
        return if (body != null) Response.success(body) else Response.success(null)
    }
}

class FakeApiServiceHttpError : ApiService {
    override suspend fun getData(url: String, appId: String?, version: String, deviceId: String?): Response<ApiCheckUpdateResponse> {
        val body = """{"message":"Server error"}""".toResponseBody("application/json".toMediaTypeOrNull())
        return Response.error(500, body)
    }
}

class FakeApiServiceThrows : ApiService {
    override suspend fun getData(url: String, appId: String?, version: String, deviceId: String?): Response<ApiCheckUpdateResponse> {
        throw IOException("network down")
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class InboxViewModelIntegrationTest {

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun integration_success_goes_to_ShowData() = runTest {
        val api = InboxViewApi(FakeApiServiceSuccess(ApiCheckUpdateResponse(
            listOf(ApiData("100", null, null, false, null, null, null, 1, null, null, "2025-09-01T10:00:00Z"))
        )))
        val vm = InboxViewModel(api)

        vm.setConfig(InboxViewServiceConfig())
        dispatcher.scheduler.advanceUntilIdle()

        assertEquals(InboxViewState.ShowData, vm.state.value)
        assertEquals(1, vm.dataList.value.size)
        assertEquals("100", vm.dataList.value.first().id)
    }

    @Test
    fun integration_http_error_results_in_NoData() = runTest {
        val api = InboxViewApi(FakeApiServiceHttpError())
        val vm = InboxViewModel(api)

        vm.setConfig(InboxViewServiceConfig())
        dispatcher.scheduler.advanceUntilIdle()

        assertEquals(InboxViewState.NoData, vm.state.value)
    }

    @Test
    fun integration_exception_results_in_Error() = runTest {
        val api = InboxViewApi(FakeApiServiceThrows())
        val vm = InboxViewModel(api)

        vm.setConfig(InboxViewServiceConfig())
        dispatcher.scheduler.advanceUntilIdle()

        assertTrue(vm.state.value is InboxViewState.Error)
    }

    @Test
    fun integration_empty_list_results_in_NoData() = runTest {
        val api = InboxViewApi(FakeApiServiceSuccess(ApiCheckUpdateResponse(emptyList())))
        val vm = InboxViewModel(api)

        vm.setConfig(InboxViewServiceConfig())
        dispatcher.scheduler.advanceUntilIdle()

        assertEquals(InboxViewState.NoData, vm.state.value)
    }

    @Test
    fun integration_null_body_results_in_NoData() = runTest {
        val api = InboxViewApi(FakeApiServiceSuccess(null))
        val vm = InboxViewModel(api)

        vm.setConfig(InboxViewServiceConfig())
        dispatcher.scheduler.advanceUntilIdle()

        assertEquals(InboxViewState.NoData, vm.state.value)
    }

    @Test
    fun integration_multiple_setConfig_overrides_state() = runTest {
        val api1 = InboxViewApi(FakeApiServiceSuccess(ApiCheckUpdateResponse(
            listOf(ApiData("first", null, null, false, null, null, null, 1, null, null, null))
        )))
        val vm = InboxViewModel(api1)

        vm.setConfig(InboxViewServiceConfig())
        dispatcher.scheduler.advanceUntilIdle()
        assertEquals("first", vm.dataList.value.first().id)

        // ساخت ویو مدل جدید با api2
        val api2 = InboxViewApi(FakeApiServiceSuccess(ApiCheckUpdateResponse(
            listOf(ApiData("second", null, null, false, null, null, null, 1, null, null, null))
        )))
        val vm2 = InboxViewModel(api2)

        vm2.setConfig(InboxViewServiceConfig())
        dispatcher.scheduler.advanceUntilIdle()
        assertEquals("second", vm2.dataList.value.first().id)
    }

    @Test
    fun integration_without_setConfig_state_remains_Initial() = runTest {
        val api = InboxViewApi(FakeApiServiceSuccess(ApiCheckUpdateResponse(emptyList())))
        val vm = InboxViewModel(api)

        dispatcher.scheduler.advanceUntilIdle()

        assertEquals(InboxViewState.Initial, vm.state.value)
    }
}
