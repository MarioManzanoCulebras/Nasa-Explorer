package com.mariomanzano.nasaexplorer.ui.screens.dailypicture

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mariomanzano.nasaexplorer.ui.screens.common.NasaItemDetailScreen
import com.mariomanzano.nasaexplorer.ui.screens.common.NasaItemsListScreen
import com.mariomanzano.nasaexplorer.usecases.FindPODUseCase
import com.mariomanzano.nasaexplorer.usecases.GetPODUseCase
import com.mariomanzano.nasaexplorer.usecases.RequestPODListUseCase

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun DailyPictureScreen(
    onClick: (com.mariomanzano.domain.entities.PictureOfDayItem) -> Unit,
    repository: com.mariomanzano.nasaexplorer.repositories.DailyPicturesRepository
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
    repository: com.mariomanzano.nasaexplorer.repositories.DailyPicturesRepository
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