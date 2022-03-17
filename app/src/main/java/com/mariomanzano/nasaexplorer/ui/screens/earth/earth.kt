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
import com.mariomanzano.nasaexplorer.repositories.MarsRepository
import com.mariomanzano.nasaexplorer.ui.screens.common.NasaItemDetailScreen
import com.mariomanzano.nasaexplorer.ui.screens.common.NasaItemsListScreen
import com.mariomanzano.nasaexplorer.usecases.FindEarthUseCase
import com.mariomanzano.nasaexplorer.usecases.GetEarthUseCase
import com.mariomanzano.nasaexplorer.usecases.RequestEarthListUseCase
import com.mariomanzano.nasaexplorer.usecases.SwitchItemToFavoriteUseCase

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun EarthScreen(
    onClick: (EarthItem) -> Unit,
    earthRepository: DailyEarthRepository
) {
    val viewModel: DailyEarthViewModel = viewModel(
        factory = DailyEarthViewModelFactory(
            GetEarthUseCase(earthRepository),
            RequestEarthListUseCase(earthRepository)
        )
    )
    val state by viewModel.state.collectAsState()

    NasaItemsListScreen(
        loading = state.loading,
        items = state.dailyPictures,
        onClick = onClick
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
    )
}