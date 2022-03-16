package com.mariomanzano.nasa_explorer.ui.screens.earth

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mariomanzano.nasa_explorer.data.entities.EarthItem
import com.mariomanzano.nasa_explorer.data.repositories.DailyEarthRepository
import com.mariomanzano.nasa_explorer.ui.screens.common.NasaItemDetailScreen
import com.mariomanzano.nasa_explorer.ui.screens.common.NasaItemsListScreen
import com.mariomanzano.nasa_explorer.usecases.FindEarthUseCase
import com.mariomanzano.nasa_explorer.usecases.GetEarthUseCase
import com.mariomanzano.nasa_explorer.usecases.RequestEarthListUseCase

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun EarthScreen(
    onClick: (EarthItem) -> Unit,
    repository: DailyEarthRepository
) {
    val viewModel: DailyEarthViewModel = viewModel(
        factory = DailyEarthViewModelFactory(
            GetEarthUseCase(repository),
            RequestEarthListUseCase(repository)
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
    repository: DailyEarthRepository
) {
    val viewModel: DailyEarthDetailViewModel = viewModel(
        factory = DailyEarthDetailViewModelFactory(
            itemId,
            FindEarthUseCase(repository)
        )
    )
    val state by viewModel.state.collectAsState()

    NasaItemDetailScreen(
        nasaItem = state.earthPicture
    )
}