package com.mariomanzano.nasaexplorer.ui.screens.favorites

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mariomanzano.domain.entities.NasaItem
import com.mariomanzano.nasaexplorer.repositories.FavoritesRepository
import com.mariomanzano.nasaexplorer.ui.screens.common.NasaItemDetailScreen
import com.mariomanzano.nasaexplorer.ui.screens.common.NasaItemsListScreen
import com.mariomanzano.nasaexplorer.usecases.FindFavoriteUseCase
import com.mariomanzano.nasaexplorer.usecases.GetFavoritesUseCase
import com.mariomanzano.nasaexplorer.usecases.SwitchFavoriteUseCase

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun FavoritesScreen(
    onClick: (NasaItem) -> Unit,
    favoritesRepository: FavoritesRepository
) {
    val viewModel: FavoriteViewModel = viewModel(
        factory = FavoriteViewModelFactory(
            GetFavoritesUseCase(favoritesRepository)
        )
    )
    val state by viewModel.state.collectAsState()

    NasaItemsListScreen(
        loading = state.loading,
        items = state.items,
        onClick = onClick
    )
}

@ExperimentalMaterialApi
@Composable
fun FavoritesDetailScreen(
    itemId: Int,
    itemType: String,
    favoritesRepository: FavoritesRepository
) {
    val viewModel: FavoriteDetailViewModel = viewModel(
        factory = FavoriteDetailViewModelFactory(
            itemId,
            itemType,
            FindFavoriteUseCase(favoritesRepository),
            SwitchFavoriteUseCase(favoritesRepository)
        )
    )
    val state by viewModel.state.collectAsState()

    NasaItemDetailScreen(
        nasaItem = state.nasaItem
    ) { viewModel.onFavoriteClicked() }
}