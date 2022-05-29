package com.mariomanzano.nasaexplorer.ui.screens.mars

import app.cash.turbine.test
import com.mariomanzano.nasaexplorer.data.database.DbMars
import com.mariomanzano.nasaexplorer.network.entities.ApiMars
import com.mariomanzano.nasaexplorer.testrules.CoroutinesTestRule
import com.mariomanzano.nasaexplorer.ui.buildDatabaseMars
import com.mariomanzano.nasaexplorer.ui.buildLastDbUpdateRepositoryWithMarsList
import com.mariomanzano.nasaexplorer.ui.buildMarsRepositoryWith
import com.mariomanzano.nasaexplorer.ui.buildRemoteMars
import com.mariomanzano.nasaexplorer.ui.screens.mars.MarsViewModel.UiState
import com.mariomanzano.nasaexplorer.usecases.GetLastMarsUpdateDateUseCase
import com.mariomanzano.nasaexplorer.usecases.GetMarsUseCase
import com.mariomanzano.nasaexplorer.usecases.RequestMarsListUseCase
import com.mariomanzano.nasaexplorer.usecases.UpdateLastMarsUpdateUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MarsIntegrationTests {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `data is loaded from local source when available`() = runTest {
        val localData = buildDatabaseMars(1, 2, 3)
        val remoteData = buildRemoteMars(4, 5, 6)
        val vm = buildViewModelWith(
            localData = localData,
            remoteData = remoteData
        )

        vm.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(loading = true), awaitItem())

            val list = awaitItem().marsPictures!!
            assertEquals("Title 3", list[0].title)
            assertEquals("Title 2", list[1].title)
            assertEquals("Title 1", list[2].title)

            cancel()
        }
    }

    private fun buildViewModelWith(
        localData: List<DbMars> = emptyList(),
        remoteData: List<ApiMars> = emptyList()
    ): MarsViewModel {
        val marsRepository = buildMarsRepositoryWith(localData, remoteData)
        val lastDbUpdateRepository = buildLastDbUpdateRepositoryWithMarsList(localData)

        val getMarsUseCase = GetMarsUseCase(marsRepository)
        val requestMarsListUseCase = RequestMarsListUseCase(marsRepository)
        val getLastMarsUpdateDateUseCase = GetLastMarsUpdateDateUseCase(lastDbUpdateRepository)
        val updateLastMarsUpdateUseCase = UpdateLastMarsUpdateUseCase(lastDbUpdateRepository)

        return MarsViewModel(
            getMarsUseCase,
            requestMarsListUseCase,
            getLastMarsUpdateDateUseCase,
            updateLastMarsUpdateUseCase
        )
    }
}