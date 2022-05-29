package com.mariomanzano.nasaexplorer.ui.screens.favorites

import app.cash.turbine.test
import com.mariomanzano.nasaexplorer.data.database.DbEarth
import com.mariomanzano.nasaexplorer.data.database.DbMars
import com.mariomanzano.nasaexplorer.data.database.DbPOD
import com.mariomanzano.nasaexplorer.testrules.CoroutinesTestRule
import com.mariomanzano.nasaexplorer.ui.buildDatabaseEarth
import com.mariomanzano.nasaexplorer.ui.buildDatabaseMars
import com.mariomanzano.nasaexplorer.ui.buildDatabasePods
import com.mariomanzano.nasaexplorer.ui.buildFavoritesRepositoryWith
import com.mariomanzano.nasaexplorer.ui.screens.favorites.FavoriteViewModel.UiState
import com.mariomanzano.nasaexplorer.usecases.GetFavoritesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class FavoritesIntegrationTests {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `data is loaded from local source when available`() = runTest {
        withContext(Dispatchers.Default) {
            val localPodData =
                buildDatabasePods(1, 2, 3).apply { iterator().forEach { it.favorite = true } }
            val localEarthData =
                buildDatabaseEarth(1, 2, 3).apply { iterator().forEach { it.favorite = true } }
            val localMarsData =
                buildDatabaseMars(1, 2, 3).apply { iterator().forEach { it.favorite = true } }
            val vm = buildViewModelWith(
                localPodData = localPodData,
                localEarthData = localEarthData,
                localMarsData = localMarsData
            )

            runCurrent()

            vm.getFavorites()

            vm.state.test {
                assertEquals(UiState(), awaitItem())
                assertEquals(UiState(loading = true, items = emptyList()), awaitItem())

                val list = awaitItem().items!!

                assertEquals("Title 3", list[0].title)
                assertEquals(true, list[0].favorite)
                assertEquals("Title 2", list[1].title)
                assertEquals(true, list[1].favorite)
                assertEquals("Title 1", list[2].title)
                assertEquals(true, list[2].favorite)

                cancel()
            }
        }
    }

    private fun buildViewModelWith(
        localPodData: List<DbPOD> = emptyList(),
        localEarthData: List<DbEarth> = emptyList(),
        localMarsData: List<DbMars> = emptyList()
    ): FavoriteViewModel {
        val favoritesRepository =
            buildFavoritesRepositoryWith(localPodData, localEarthData, localMarsData)

        val getFavoritesUseCase = GetFavoritesUseCase(favoritesRepository)

        return FavoriteViewModel(getFavoritesUseCase)
    }
}