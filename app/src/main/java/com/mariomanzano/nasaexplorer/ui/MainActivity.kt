package com.mariomanzano.nasaexplorer.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.mariomanzano.nasaexplorer.data.database.NasaRoomDataSource
import com.mariomanzano.nasaexplorer.repositories.LastDbUpdateRepository
import com.mariomanzano.nasaexplorer.ui.common.app
import com.mariomanzano.nasaexplorer.usecases.*

@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val nasaRoomDataSource = NasaRoomDataSource(app.db.nasaDao())
        val lastUpdateRepository = LastDbUpdateRepository(nasaRoomDataSource)
        viewModel = MainViewModel(
            GetLastPODUpdateDateUseCase(lastUpdateRepository),
            GetLastEarthUpdateDateNeedUseCase(lastUpdateRepository),
            GetLastMarsUpdateDateUseCase(lastUpdateRepository),
            UpdateLastPODUpdateUseCase(lastUpdateRepository),
            UpdateLastEarthUpdateUseCase(lastUpdateRepository),
            UpdateLastMarsUpdateUseCase(lastUpdateRepository)
        )

        setContent {
            NasaExploreApp()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateDates()
    }
}