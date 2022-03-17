package com.mariomanzano.nasaexplorer.ui.screens.mars

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mariomanzano.domain.entities.MarsItem
import com.mariomanzano.nasaexplorer.repositories.DailyEarthRepository
import com.mariomanzano.nasaexplorer.repositories.DailyPicturesRepository
import com.mariomanzano.nasaexplorer.repositories.MarsRepository
import com.mariomanzano.nasaexplorer.ui.screens.common.NasaItemDetailScreen
import com.mariomanzano.nasaexplorer.ui.screens.common.NasaItemsListScreen
import com.mariomanzano.nasaexplorer.usecases.FindMarsUseCase
import com.mariomanzano.nasaexplorer.usecases.GetMarsUseCase
import com.mariomanzano.nasaexplorer.usecases.RequestMarsListUseCase
import com.mariomanzano.nasaexplorer.usecases.SwitchItemToFavoriteUseCase

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun MarsScreen(
    onClick: (MarsItem) -> Unit,
    dailyPicturesRepository: DailyPicturesRepository,
    earthRepository: DailyEarthRepository,
    marsRepository: MarsRepository
) {
    val viewModel: MarsViewModel = viewModel(
        factory = MarsViewModelFactory(
            GetMarsUseCase(marsRepository),
            RequestMarsListUseCase(marsRepository),
            SwitchItemToFavoriteUseCase(dailyPicturesRepository, earthRepository, marsRepository)
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