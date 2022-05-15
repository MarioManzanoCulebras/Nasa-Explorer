package com.mariomanzano.nasaexplorer.ui.screens.earth

import app.cash.turbine.test
import com.devexperto.architectcoders.testshared.sampleEarth
import com.devexperto.architectcoders.testshared.sampleLastUpdateInfo
import com.mariomanzano.nasaexplorer.testrules.CoroutinesTestRule
import com.mariomanzano.nasaexplorer.ui.screens.earth.DailyEarthViewModel.UiState
import com.mariomanzano.nasaexplorer.usecases.GetEarthUseCase
import com.mariomanzano.nasaexplorer.usecases.GetLastEarthUpdateDateUseCase
import com.mariomanzano.nasaexplorer.usecases.RequestEarthListUseCase
import com.mariomanzano.nasaexplorer.usecases.UpdateLastEarthUpdateUseCase
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
class DailyEarthViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var getEarthUseCase: GetEarthUseCase

    @Mock
    lateinit var requestEarthListUseCase: RequestEarthListUseCase

    @Mock
    lateinit var getLastEarthUpdateDateUseCase: GetLastEarthUpdateDateUseCase

    @Mock
    lateinit var updateLastEarthUpdateUseCase: UpdateLastEarthUpdateUseCase

    private lateinit var vm: DailyEarthViewModel

    private val list = listOf(sampleEarth.copy(id = 1))

    @Before
    fun setup() {
        whenever(getEarthUseCase()).thenReturn(flowOf(list))
        whenever(getLastEarthUpdateDateUseCase()).thenReturn(flowOf(sampleLastUpdateInfo))
        vm = DailyEarthViewModel(
            getEarthUseCase,
            requestEarthListUseCase,
            getLastEarthUpdateDateUseCase,
            updateLastEarthUpdateUseCase
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
        vm.launchUpdate()

        vm.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(loading = true), awaitItem())
            assertEquals(UiState(loading = false, dailyPictures = list), awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

}