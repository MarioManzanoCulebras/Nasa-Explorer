package com.mariomanzano.nasaexplorer.ui.screens.earth

import app.cash.turbine.test
import com.mariomanzano.nasaexplorer.data.database.DbEarth
import com.mariomanzano.nasaexplorer.network.entities.ApiEPIC
import com.mariomanzano.nasaexplorer.testrules.CoroutinesTestRule
import com.mariomanzano.nasaexplorer.ui.buildDailyEarthRepositoryWith
import com.mariomanzano.nasaexplorer.ui.buildDatabaseEarth
import com.mariomanzano.nasaexplorer.ui.buildLastDbUpdateRepositoryWithEarthList
import com.mariomanzano.nasaexplorer.ui.buildRemoteEarth
import com.mariomanzano.nasaexplorer.ui.screens.earth.DailyEarthViewModel.UiState
import com.mariomanzano.nasaexplorer.usecases.GetEarthUseCase
import com.mariomanzano.nasaexplorer.usecases.GetLastEarthUpdateDateUseCase
import com.mariomanzano.nasaexplorer.usecases.RequestEarthListUseCase
import com.mariomanzano.nasaexplorer.usecases.UpdateLastEarthUpdateUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DailyEarthIntegrationTests {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `data is loaded from local source when available`() = runTest {
        val localData = buildDatabaseEarth(1, 2, 3)
        val remoteData = buildRemoteEarth(4, 5, 6)
        val vm = buildViewModelWith(
            localData = localData,
            remoteData = remoteData
        )

        vm.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(loading = true), awaitItem())

            val list = awaitItem().dailyPictures!!
            assertEquals("Title 3", list[0].title)
            assertEquals("Title 2", list[1].title)
            assertEquals("Title 1", list[2].title)

            cancel()
        }
    }

    private fun buildViewModelWith(
        localData: List<DbEarth> = emptyList(),
        remoteData: List<ApiEPIC> = emptyList()
    ): DailyEarthViewModel {
        val dailyEarthRepository = buildDailyEarthRepositoryWith(localData, remoteData)
        val lastDbUpdateRepository = buildLastDbUpdateRepositoryWithEarthList(localData)

        val getEarthUseCase = GetEarthUseCase(dailyEarthRepository)
        val requestEarthListUseCase = RequestEarthListUseCase(dailyEarthRepository)
        val getLastEarthUpdateDateUseCase = GetLastEarthUpdateDateUseCase(lastDbUpdateRepository)
        val updateLastEarthUpdateUseCase = UpdateLastEarthUpdateUseCase(lastDbUpdateRepository)

        return DailyEarthViewModel(
            getEarthUseCase,
            requestEarthListUseCase,
            getLastEarthUpdateDateUseCase,
            updateLastEarthUpdateUseCase
        )
    }
}