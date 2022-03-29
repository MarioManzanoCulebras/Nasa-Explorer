package com.mariomanzano.nasaexplorer.ui.screens.earth

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mariomanzano.domain.entities.EarthItem
import com.mariomanzano.nasaexplorer.repositories.DailyEarthRepository
import com.mariomanzano.nasaexplorer.repositories.DailyPicturesRepository
import com.mariomanzano.nasaexplorer.repositories.LastDbUpdateRepository
import com.mariomanzano.nasaexplorer.repositories.MarsRepository
import com.mariomanzano.nasaexplorer.ui.screens.common.NasaItemDetailScreen
import com.mariomanzano.nasaexplorer.ui.screens.common.NasaItemsListScreen
import com.mariomanzano.nasaexplorer.usecases.*

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun EarthScreen(
    onClick: (EarthItem) -> Unit,
    earthRepository: DailyEarthRepository,
    lastDbUpdateRepository: LastDbUpdateRepository
) {
    val viewModel: DailyEarthViewModel = viewModel(
        factory = DailyEarthViewModelFactory(
            GetEarthUseCase(earthRepository),
            RequestEarthListUseCase(earthRepository),
            GetLastEarthUpdateDateNeedUseCase(lastDbUpdateRepository),
            UpdateLastEarthUpdateUseCase(lastDbUpdateRepository)
        )
    )
    val state by viewModel.state.collectAsState()

    NasaItemsListScreen(
        loading = state.loading,
        items = state.dailyPictures,
        onClick = onClick,
        onRefresh = { viewModel.launchUpdate() }
    )
}

@ExperimentalMaterialApi
@Composable
fun EarthDetailScreen(
    itemId: Int,
    dailyPicturesRepository: DailyPicturesRepository,
    earthRepository: DailyEarthRepository,
    marsRepository: MarsRepository
) {
    val viewModel: DailyEarthDetailViewModel = viewModel(
        factory = DailyEarthDetailViewModelFactory(
            itemId,
            FindEarthUseCase(earthRepository),
            SwitchItemToFavoriteUseCase(dailyPicturesRepository, earthRepository, marsRepository)
        )
    )
    val state by viewModel.state.collectAsState()

    NasaItemDetailScreen(
        nasaItem = state.earthPicture
    ) { viewModel.onFavoriteClicked() }
}