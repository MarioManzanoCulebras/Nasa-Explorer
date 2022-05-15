package com.mariomanzano.nasaexplorer.ui.screens.dailypicture

import app.cash.turbine.test
import com.devexperto.architectcoders.testshared.sampleLastUpdateInfo
import com.devexperto.architectcoders.testshared.samplePOD
import com.mariomanzano.nasaexplorer.testrules.CoroutinesTestRule
import com.mariomanzano.nasaexplorer.ui.screens.dailypicture.DailyPictureViewModel.UiState
import com.mariomanzano.nasaexplorer.usecases.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DailyPicturesViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var getPODUseCase: GetPODUseCase

    @Mock
    lateinit var requestPODListUseCase: RequestPODListUseCase

    @Mock
    lateinit var requestPODSingleDayUseCase: RequestPODSingleDayUseCase

    @Mock
    lateinit var getLastPODUpdateDateUseCase: GetLastPODUpdateDateUseCase

    @Mock
    lateinit var updateLastPODUpdateUseCase: UpdateLastPODUpdateUseCase

    private lateinit var vm: DailyPictureViewModel

    private val list = listOf(samplePOD.copy(id = 1))

    @Before
    fun setup() {
        whenever(getPODUseCase()).thenReturn(flowOf(list))
        whenever(getLastPODUpdateDateUseCase()).thenReturn(flowOf(sampleLastUpdateInfo))
        vm = DailyPictureViewModel(
            getPODUseCase,
            requestPODListUseCase,
            requestPODSingleDayUseCase,
            getLastPODUpdateDateUseCase,
            updateLastPODUpdateUseCase
        )
    }

    @Test
    fun `State is updated with current cached content immediately`() = runTest {
        vm.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(loading = true), awaitItem())
            assertEquals(UiState(loading = false, dailyPictures = list), awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `Request manual from single Day request after delay`() = runTest {
        vm.launchDayRequest()

        vm.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(loading = true), awaitItem())
            assertEquals(UiState(loading = false, dailyPictures = list), awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `Request full list complete Request produce empty list at first and then request to server`() =
        runTest {
            vm.launchListCompleteRequest()

            vm.state.test {
                assertEquals(UiState(), awaitItem())
                assertEquals(UiState(loading = true), awaitItem())
                assertEquals(UiState(loading = false, dailyPictures = list), awaitItem())
                cancelAndConsumeRemainingEvents()
            }
        }

}