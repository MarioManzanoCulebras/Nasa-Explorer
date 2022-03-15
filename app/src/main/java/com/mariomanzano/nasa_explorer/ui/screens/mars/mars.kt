package com.mariomanzano.nasa_explorer.ui.screens.mars

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mariomanzano.nasa_explorer.data.entities.MarsItem
import com.mariomanzano.nasa_explorer.ui.screens.common.MarsItemDetailScreen
import com.mariomanzano.nasa_explorer.ui.screens.common.NasaItemsListScreen

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun MarsScreen(
    onClick: (MarsItem) -> Unit,
    viewModel: MarsViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    NasaItemsListScreen(
        loading = state.loading,
        items = state.marsPictures,
        onClick = onClick
    )
}

@ExperimentalMaterialApi
@Composable
fun MarsDetailScreen(viewModel: MarsDetailViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()

    MarsItemDetailScreen(
        loading = state.loading,
        marsItem = state.marsItem
    )
}