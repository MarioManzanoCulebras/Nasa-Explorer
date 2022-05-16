package com.mariomanzano.nasaexplorer.ui.screens.dailypicture

import app.cash.turbine.test
import com.mariomanzano.nasaexplorer.data.database.DbPOD
import com.mariomanzano.nasaexplorer.network.entities.ApiAPOD
import com.mariomanzano.nasaexplorer.testrules.CoroutinesTestRule
import com.mariomanzano.nasaexplorer.ui.buildDailyPictureRepositoryWith
import com.mariomanzano.nasaexplorer.ui.buildDatabasePods
import com.mariomanzano.nasaexplorer.ui.buildLastDbUpdateRepositoryWith
import com.mariomanzano.nasaexplorer.ui.buildRemotePods
import com.mariomanzano.nasaexplorer.ui.screens.dailypicture.DailyPictureViewModel.UiState
import com.mariomanzano.nasaexplorer.usecases.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DailyPicturesIntegrationTests {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `data is loaded from local source when available`() = runTest {
        val localData = buildDatabasePods(1, 2, 3)
        val remoteData = buildRemotePods(4, 5, 6)
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


    @Test
    fun `data is loaded from server when launchListCompleteRequest is called`() = runTest {
        val remoteData = buildRemotePods(4, 5, 6)
        val vm = buildViewModelWith(
            localData = emptyList(),
            remoteData = remoteData
        )
        runCurrent()

        vm.launchListCompleteRequest()

        vm.state.test {
            val list = awaitItem().dailyPictures!!
            assertEquals("Title 4", list[0].title)
            assertEquals("Title 5", list[1].title)
            assertEquals("Title 6", list[2].title)


            cancel()
        }
    }

    @Test
    fun `data is loaded from server when launchDayRequest is called`() = runTest {
        val remoteData = buildRemotePods(4, 5, 6)
        val vm = buildViewModelWith(
            localData = emptyList(),
            remoteData = remoteData
        )
        runCurrent()

        vm.launchDayRequest()

        vm.state.test {
            val list = awaitItem().dailyPictures!!
            assertEquals("Title 4", list[0].title)

            cancel()
        }
    }

    private fun buildViewModelWith(
        localData: List<DbPOD> = emptyList(),
        remoteData: List<ApiAPOD> = emptyList()
    ): DailyPictureViewModel {
        val dailyPicturesRepository = buildDailyPictureRepositoryWith(localData, remoteData)
        val lastDbUpdateRepository = buildLastDbUpdateRepositoryWith(localData)

        val getPODUseCase = GetPODUseCase(dailyPicturesRepository)
        val requestPODListUseCase = RequestPODListUseCase(dailyPicturesRepository)
        val requestPODSingleDayUseCase = RequestPODSingleDayUseCase(dailyPicturesRepository)
        val getLastPODUpdateDateUseCase = GetLastPODUpdateDateUseCase(lastDbUpdateRepository)
        val updateLastPODUpdateUseCase = UpdateLastPODUpdateUseCase(lastDbUpdateRepository)

        return DailyPictureViewModel(
            getPODUseCase,
            requestPODListUseCase,
            requestPODSingleDayUseCase,
            getLastPODUpdateDateUseCase,
            updateLastPODUpdateUseCase
        )
    }
}