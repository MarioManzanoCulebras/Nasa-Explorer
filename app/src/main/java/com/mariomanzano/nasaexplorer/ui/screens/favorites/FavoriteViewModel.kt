package com.mariomanzano.nasaexplorer.ui.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mariomanzano.domain.Error
import com.mariomanzano.domain.entities.NasaItem
import com.mariomanzano.nasaexplorer.network.toError
import com.mariomanzano.nasaexplorer.usecases.GetFavoritesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val getFavoritesUseCase: GetFavoritesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()

    private val favoriteList: MutableList<NasaItem> = mutableListOf()

    init {
        viewModelScope.launch {
            getFavoritesUseCase()
                .catch { cause -> _state.update { it.copy(error = cause.toError()) } }
                .collect { items ->
                    favoriteList.addAll(items.filter { it.favorite }.sortedBy { it.type })
                    _state.update {
                        UiState(items = favoriteList)
                    }
                }
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val items: List<NasaItem>? = null,
        val error: Error? = null
    )
}

@Suppress("UNCHECKED_CAST")
class FavoriteViewModelFactory(
    private val getFavoritesUseCase: GetFavoritesUseCase
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavoriteViewModel(getFavoritesUseCase) as T
    }
}