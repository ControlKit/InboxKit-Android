package com.sepanta.controlkit.inboxviewkit.view.viewmodel/*
 *  File: InboxViewModelTest.kt
 *
 *  Created by morteza on 9/1/25.
 */



import app.cash.turbine.test
import com.sepanta.controlkit.inboxviewkit.config.InboxViewServiceConfig
import com.sepanta.controlkit.inboxviewkit.service.InboxViewApi
import com.sepanta.controlkit.inboxviewkit.service.apiError.ApiError
import com.sepanta.controlkit.inboxviewkit.service.apiError.NetworkResult
import com.sepanta.controlkit.inboxviewkit.service.model.ApiCheckUpdateResponse
import com.sepanta.controlkit.inboxviewkit.service.model.ApiData
import com.sepanta.controlkit.inboxviewkit.view.viewModel.InboxViewModel
import com.sepanta.controlkit.inboxviewkit.view.viewModel.state.InboxViewState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class InboxViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private lateinit var viewModel: InboxViewModel
    private lateinit var api: InboxViewApi

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        api = mockk()
        viewModel = InboxViewModel(api)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun initial_state_is_Initial() {
        assertEquals(InboxViewState.Initial, viewModel.state.value)
        assertTrue(viewModel.dataList.value.isEmpty())
    }

    @Test
    fun setConfig_success_returns_ShowData() = runTest {
        val mockResponse = ApiCheckUpdateResponse(
            listOf(ApiData("1", null, null, false, null, null, null, 1, null, null, null))
        )
        coEvery { api.getInboxViewData(any(), any(), any(), any()) } returns NetworkResult.Success(mockResponse)

        viewModel.setConfig(InboxViewServiceConfig())
        dispatcher.scheduler.advanceUntilIdle()

        assertEquals(InboxViewState.ShowData, viewModel.state.value)
        assertTrue(viewModel.dataList.value.isNotEmpty())
    }

    @Test
    fun setConfig_success_null_returns_NoData() = runTest {
        coEvery { api.getInboxViewData(any(), any(), any(), any()) } returns NetworkResult.Success(null)

        viewModel.setConfig(InboxViewServiceConfig())
        dispatcher.scheduler.advanceUntilIdle()

        assertEquals(InboxViewState.NoData, viewModel.state.value)
    }

    @Test
    fun api_error_returns_Error_state() = runTest {
        val error = ApiError("Boom", ApiError.ErrorStatus.UNKNOWN_ERROR)
        coEvery { api.getInboxViewData(any(), any(), any(), any()) } returns NetworkResult.Error(error)

        viewModel.setConfig(InboxViewServiceConfig())
        dispatcher.scheduler.advanceUntilIdle()

        assertTrue(viewModel.state.value is InboxViewState.Error)
    }

    @Test
    fun setCurrentIndex_updates_model_and_buttons() = runTest {
        val mockResponse = ApiCheckUpdateResponse(
            listOf(
                ApiData("1", null, null, false, null, null, null, 1, null, null, null),
                ApiData("2", null, null, false, null, null, null, 1, null, null, null)
            )
        )
        coEvery { api.getInboxViewData(any(), any(), any(), any()) } returns NetworkResult.Success(mockResponse)

        viewModel.setConfig(InboxViewServiceConfig())
        dispatcher.scheduler.advanceUntilIdle()

        viewModel.setCurrentIndex(1)

        assertEquals("2", viewModel.currentModel.value.id)
        assertTrue(viewModel.previousButtonState.value)
        assertFalse(viewModel.nextButtonState.value)
    }

    @Test
    fun submitDialog_sets_showDetailPage_and_index() = runTest {
        val mockResponse = ApiCheckUpdateResponse(
            listOf(ApiData("10", null, null, false, null, null, null, 1, null, null, null))
        )
        coEvery { api.getInboxViewData(any(), any(), any(), any()) } returns NetworkResult.Success(mockResponse)

        viewModel.setConfig(InboxViewServiceConfig())
        dispatcher.scheduler.advanceUntilIdle()

        viewModel.submitDialog(0)

        assertTrue(viewModel.showDetailPage.value)
        assertEquals("10", viewModel.currentModel.value.id)
    }

    @Test
    fun dismissDialog_resets_state_and_triggers_launchAlert() = runTest {
        val mockResponse = ApiCheckUpdateResponse(
            listOf(ApiData("1", null, null, false, null, null, null, 1, null, null, null))
        )
        coEvery { api.getInboxViewData(any(), any(), any(), any()) } returns NetworkResult.Success(mockResponse)

        viewModel.setConfig(InboxViewServiceConfig())
        dispatcher.scheduler.advanceUntilIdle()

        viewModel.dismissDialog()

        assertEquals(InboxViewState.Initial, viewModel.state.value)
        assertFalse(viewModel.showDetailPage.value)

        viewModel.launchAlertEvent.test {
            assertNotNull(awaitItem())
        }
    }
    @Test
    fun single_item_dataList_disables_navigation_buttons() = runTest {
        val mockResponse = ApiCheckUpdateResponse(
            listOf(ApiData("solo", null, null, false, null, null, null, 1, null, null, null))
        )
        coEvery { api.getInboxViewData(any(), any(), any(), any()) } returns NetworkResult.Success(mockResponse)

        viewModel.setConfig(InboxViewServiceConfig())
        dispatcher.scheduler.advanceUntilIdle()

        viewModel.setCurrentIndex(0)

        assertFalse(viewModel.nextButtonState.value)
        assertFalse(viewModel.previousButtonState.value)
    }

}


