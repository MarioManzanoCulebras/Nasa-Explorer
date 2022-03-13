package com.mariomanzano.nasa_explorer.ui.screens.earth

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mariomanzano.nasa_explorer.data.entities.EarthItem
import com.mariomanzano.nasa_explorer.ui.screens.common.NasaItemDetailScreen
import com.mariomanzano.nasa_explorer.ui.screens.common.NasaItemsListScreen

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun EarthScreen(
    onClick: (EarthItem) -> Unit,
    viewModel: DailyEarthViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    NasaItemsListScreen(
        loading = state.loading,
        items = state.dailyPictures,
        onClick = onClick
    )
}

@ExperimentalMaterialApi
@Composable
fun EarthDetailScreen(viewModel: DailyEarthDetailViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()

    NasaItemDetailScreen(
        loading = state.loading,
        nasaItem = state.dailyPicture
    )
}