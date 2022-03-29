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
import com.mariomanzano.nasaexplorer.repositories.LastDbUpdateRepository
import com.mariomanzano.nasaexplorer.repositories.MarsRepository
import com.mariomanzano.nasaexplorer.ui.screens.common.NasaItemDetailScreen
import com.mariomanzano.nasaexplorer.ui.screens.common.NasaItemsListScreen
import com.mariomanzano.nasaexplorer.usecases.*

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun MarsScreen(
    onClick: (MarsItem) -> Unit,
    marsRepository: MarsRepository,
    lastDbUpdateRepository: LastDbUpdateRepository
) {
    val viewModel: MarsViewModel = viewModel(
        factory = MarsViewModelFactory(
            GetMarsUseCase(marsRepository),
            RequestMarsListUseCase(marsRepository),
            GetLastMarsUpdateDateUseCase(lastDbUpdateRepository),
            UpdateLastMarsUpdateUseCase(lastDbUpdateRepository)
        )
    )
    val state by viewModel.state.collectAsState()

    NasaItemsListScreen(
        loading = state.loading,
        items = state.marsPictures,
        onClick = onClick,
        onRefresh = { viewModel.launchUpdate() }
    )
}

@ExperimentalMaterialApi
@Composable
fun MarsDetailScreen(
    itemId: Int,
    dailyPicturesRepository: DailyPicturesRepository,
    earthRepository: DailyEarthRepository,
    marsRepository: MarsRepository
) {
    val viewModel: MarsDetailViewModel = viewModel(
        factory = MarsDetailViewModelFactory(
            itemId,
            FindMarsUseCase(marsRepository),
            SwitchItemToFavoriteUseCase(dailyPicturesRepository, earthRepository, marsRepository)
        )
    )
    val state by viewModel.state.collectAsState()

    NasaItemDetailScreen(
        nasaItem = state.marsItem
    ) { viewModel.onFavoriteClicked() }
}