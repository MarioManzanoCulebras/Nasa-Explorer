package com.mariomanzano.nasaexplorer.ui.screens.favorites

import app.cash.turbine.test
import com.devexperto.architectcoders.testshared.sampleEarth
import com.devexperto.architectcoders.testshared.sampleMars
import com.devexperto.architectcoders.testshared.samplePOD
import com.mariomanzano.domain.Error
import com.mariomanzano.nasaexplorer.testrules.CoroutinesTestRule
import com.mariomanzano.nasaexplorer.ui.screens.favorites.FavoriteViewModel.UiState
import com.mariomanzano.nasaexplorer.usecases.GetFavoritesUseCase
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
class FavoriteViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var getFavoritesUseCase: GetFavoritesUseCase

    private lateinit var vm: FavoriteViewModel

    private val list =
        listOf(samplePOD.copy(id = 1), sampleEarth.copy(id = 1), sampleMars.copy(id = 1))

    @Before
    fun setup() {
        vm = FavoriteViewModel(getFavoritesUseCase)
    }

    @Test
    fun `State is updated with current cached content and not empty list immediately`() = runTest {
        whenever(getFavoritesUseCase()).thenReturn(flowOf(flowOf(list)))

        vm.getFavorites()

        vm.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(loading = true, items = emptyList()), awaitItem())
            assertEquals(
                UiState(
                    loading = false,
                    items = list.sortedByDescending { favorite -> favorite.date }), awaitItem()
            )
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `State is updated with NoData error screen state when favorites list is empty`() = runTest {
        whenever(getFavoritesUseCase()).thenReturn(flowOf(flowOf(emptyList())))

        vm.getFavorites()

        vm.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(loading = true, items = emptyList()), awaitItem())
            assertEquals(
                UiState(loading = false, items = emptyList(), error = Error.NoData),
                awaitItem()
            )
            cancelAndConsumeRemainingEvents()
        }
    }

}