package com.mariomanzano.nasaexplorer.ui.screens.earth

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.mariomanzano.domain.entities.EarthItem
import com.mariomanzano.nasaexplorer.ui.screens.common.NasaItemDetailScreen
import com.mariomanzano.nasaexplorer.ui.screens.common.NasaItemsListScreen

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun EarthScreen(
    listState: LazyGridState,
    onClick: (EarthItem) -> Unit,
    onItemsMoreClicked: () -> Unit,
    viewModel: DailyEarthViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    NasaItemsListScreen(
        loading = state.loading,
        items = state.dailyPictures,
        onClick = onClick,
        onRefreshComplete = { viewModel.launchUpdate() },
        onSimpleRefresh = { viewModel.launchUpdate() },
        error = state.error,
        listState = listState,
        onItemsMoreClicked = onItemsMoreClicked
    )
}

@ExperimentalMaterialApi
@Composable
fun EarthDetailScreen(viewModel: DailyEarthDetailViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()

    NasaItemDetailScreen(
        nasaItem = state.earthPicture
    ) { viewModel.onFavoriteClicked() }
}