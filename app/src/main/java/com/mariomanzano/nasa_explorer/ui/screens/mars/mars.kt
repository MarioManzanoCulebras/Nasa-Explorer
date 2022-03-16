package com.mariomanzano.nasa_explorer.ui.screens.mars

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mariomanzano.nasa_explorer.data.entities.MarsItem
import com.mariomanzano.nasa_explorer.data.repositories.MarsRepository
import com.mariomanzano.nasa_explorer.ui.screens.common.NasaItemDetailScreen
import com.mariomanzano.nasa_explorer.ui.screens.common.NasaItemsListScreen
import com.mariomanzano.nasa_explorer.usecases.FindMarsUseCase
import com.mariomanzano.nasa_explorer.usecases.GetMarsUseCase
import com.mariomanzano.nasa_explorer.usecases.RequestMarsListUseCase

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun MarsScreen(
    onClick: (MarsItem) -> Unit,
    repository: MarsRepository
) {
    val viewModel: MarsViewModel = viewModel(
        factory = MarsViewModelFactory(
            GetMarsUseCase(repository),
            RequestMarsListUseCase(repository)
        )
    )
    val state by viewModel.state.collectAsState()

    NasaItemsListScreen(
        loading = state.loading,
        items = state.marsPictures,
        onClick = onClick
    )
}

@ExperimentalMaterialApi
@Composable
fun MarsDetailScreen(
    itemId: Int,
    repository: MarsRepository
) {
    val viewModel: MarsDetailViewModel = viewModel(
        factory = MareDetailViewModelFactory(
            itemId,
            FindMarsUseCase(repository)
        )
    )
    val state by viewModel.state.collectAsState()

    NasaItemDetailScreen(
        nasaItem = state.marsItem
    )
}