package com.mariomanzano.nasaexplorer.ui.screens.dailypicture

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.mariomanzano.domain.entities.PictureOfDayItem
import com.mariomanzano.nasaexplorer.ui.screens.common.NasaItemDetailScreen
import com.mariomanzano.nasaexplorer.ui.screens.common.PODItemsListScreen

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun DailyPictureScreen(
    listState: LazyListState,
    onClick: (PictureOfDayItem) -> Unit,
    viewModel: DailyPictureViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    PODItemsListScreen(
        loading = state.loading,
        items = state.dailyPictures,
        onClick = onClick,
        onRefreshComplete = { viewModel.launchListCompleteRequest() },
        onSimpleRefresh = { viewModel.launchDayRequest() },
        state.error,
        listState = listState
    )
}

@ExperimentalMaterialApi
@Composable
fun DailyPictureDetailScreen(viewModel: DailyPictureDetailViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()

    NasaItemDetailScreen(
        nasaItem = state.dailyPicture
    ) { viewModel.onFavoriteClicked() }
}