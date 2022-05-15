package com.mariomanzano.nasaexplorer.ui.screens.mars

import app.cash.turbine.test
import com.devexperto.architectcoders.testshared.sampleLastUpdateInfo
import com.devexperto.architectcoders.testshared.sampleMars
import com.mariomanzano.nasaexplorer.testrules.CoroutinesTestRule
import com.mariomanzano.nasaexplorer.ui.screens.mars.MarsViewModel.UiState
import com.mariomanzano.nasaexplorer.usecases.GetLastMarsUpdateDateUseCase
import com.mariomanzano.nasaexplorer.usecases.GetMarsUseCase
import com.mariomanzano.nasaexplorer.usecases.RequestMarsListUseCase
import com.mariomanzano.nasaexplorer.usecases.UpdateLastMarsUpdateUseCase
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
class MarsViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var getMarsUseCase: GetMarsUseCase

    @Mock
    lateinit var requestMarsListUseCase: RequestMarsListUseCase

    @Mock
    lateinit var getLastMarsDateUseCase: GetLastMarsUpdateDateUseCase

    @Mock
    lateinit var updateLasMarsUpdateUseCase: UpdateLastMarsUpdateUseCase

    private lateinit var vm: MarsViewModel

    private val list = listOf(sampleMars.copy(id = 1))

    @Before
    fun setup() {
        whenever(getMarsUseCase()).thenReturn(flowOf(list))
        whenever(getLastMarsDateUseCase()).thenReturn(flowOf(sampleLastUpdateInfo))
        vm = MarsViewModel(
            getMarsUseCase,
            requestMarsListUseCase,
            getLastMarsDateUseCase,
            updateLasMarsUpdateUseCase
        )
    }

    @Test
    fun `State is updated with current cached content immediately`() = runTest {
        vm.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(loading = true), awaitItem())
            assertEquals(UiState(loading = false, marsPictures = list), awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `Request manual from single Day request after delay`() = runTest {
        vm.launchUpdate()

        vm.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(loading = true), awaitItem())
            assertEquals(UiState(loading = false, marsPictures = list), awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

}