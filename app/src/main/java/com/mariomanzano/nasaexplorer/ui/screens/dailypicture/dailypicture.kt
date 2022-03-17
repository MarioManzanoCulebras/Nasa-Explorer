package com.mariomanzano.nasaexplorer.ui.screens.dailypicture

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mariomanzano.nasaexplorer.repositories.DailyEarthRepository
import com.mariomanzano.nasaexplorer.repositories.DailyPicturesRepository
import com.mariomanzano.nasaexplorer.repositories.MarsRepository
import com.mariomanzano.nasaexplorer.ui.screens.common.NasaItemDetailScreen
import com.mariomanzano.nasaexplorer.ui.screens.common.PODItemsListScreen
import com.mariomanzano.nasaexplorer.usecases.FindPODUseCase
import com.mariomanzano.nasaexplorer.usecases.GetPODUseCase
import com.mariomanzano.nasaexplorer.usecases.RequestPODListUseCase
import com.mariomanzano.nasaexplorer.usecases.SwitchItemToFavoriteUseCase

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun DailyPictureScreen(
    onClick: (com.mariomanzano.domain.entities.PictureOfDayItem) -> Unit,
    dailyPicturesRepository: DailyPicturesRepository
) {
    val viewModel: DailyPictureViewModel = viewModel(
        factory = DailyPictureViewModelFactory(
            GetPODUseCase(dailyPicturesRepository),
            RequestPODListUseCase(dailyPicturesRepository)
        )
    )
    val state by viewModel.state.collectAsState()

    PODItemsListScreen(
        loading = state.loading,
        items = state.dailyPictures,
        onClick = onClick
    )
}

@ExperimentalMaterialApi
@Composable
fun DailyPictureDetailScreen(
    itemId: Int,
    dailyPicturesRepository: DailyPicturesRepository,
    earthRepository: DailyEarthRepository,
    marsRepository: MarsRepository
) {
    val viewModel: DailyPictureDetailViewModel = viewModel(
        factory = DailyPictureDetailViewModelFactory(
            itemId,
            FindPODUseCase(dailyPicturesRepository),
            SwitchItemToFavoriteUseCase(dailyPicturesRepository, earthRepository, marsRepository)
        )
    )
    val state by viewModel.state.collectAsState()

    NasaItemDetailScreen(
        nasaItem = state.dailyPicture
    )
}