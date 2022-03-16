package com.mariomanzano.nasa_explorer.ui.screens.dailypicture

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mariomanzano.nasa_explorer.data.entities.PictureOfDayItem
import com.mariomanzano.nasa_explorer.data.repositories.DailyPicturesRepository
import com.mariomanzano.nasa_explorer.ui.screens.common.NasaItemDetailScreen
import com.mariomanzano.nasa_explorer.ui.screens.common.NasaItemsListScreen
import com.mariomanzano.nasa_explorer.usecases.FindPODUseCase
import com.mariomanzano.nasa_explorer.usecases.GetPODUseCase
import com.mariomanzano.nasa_explorer.usecases.RequestPODListUseCase

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun DailyPictureScreen(
    onClick: (PictureOfDayItem) -> Unit,
    repository: DailyPicturesRepository
) {
    val viewModel: DailyPictureViewModel = viewModel(
        factory = DailyPictureViewModelFactory(
            GetPODUseCase(repository),
            RequestPODListUseCase(repository)
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
fun DailyPictureDetailScreen(
    itemId: Int,
    repository: DailyPicturesRepository
) {
    val viewModel: DailyPictureDetailViewModel = viewModel(
        factory = DailyPictureDetailViewModelFactory(
            itemId,
            FindPODUseCase(repository)
        )
    )
    val state by viewModel.state.collectAsState()

    NasaItemDetailScreen(
        nasaItem = state.dailyPicture
    )
}