package com.mariomanzano.nasaexplorer.ui.screens.mars

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.mariomanzano.domain.entities.MarsItem
import com.mariomanzano.nasaexplorer.ui.screens.common.NasaItemDetailScreen
import com.mariomanzano.nasaexplorer.ui.screens.common.NasaItemsListScreen

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun MarsScreen(
    listState: LazyGridState,
    onClick: (MarsItem) -> Unit,
    onItemsMoreClicked: () -> Unit,
    viewModel: MarsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    NasaItemsListScreen(
        loading = state.loading,
        items = state.marsPictures,
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
fun MarsDetailScreen(viewModel: MarsDetailViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()

    NasaItemDetailScreen(
        nasaItem = state.marsItem
    ) { viewModel.onFavoriteClicked() }
}