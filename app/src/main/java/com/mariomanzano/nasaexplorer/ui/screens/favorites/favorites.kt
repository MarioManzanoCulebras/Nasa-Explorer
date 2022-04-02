package com.mariomanzano.nasaexplorer.ui.screens.favorites

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
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

    val lifecycleOwner = LocalLifecycleOwner.current
    val observer = remember {
        LifecycleEventObserver { _, event ->
            if (event.targetState.isAtLeast(Lifecycle.State.RESUMED)) {
                viewModel.getFavorites()
            }
        }
    }

    DisposableEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    NasaItemsListScreen(
        loading = state.loading,
        items = state.items,
        onClick = onClick,
        error = state.error
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