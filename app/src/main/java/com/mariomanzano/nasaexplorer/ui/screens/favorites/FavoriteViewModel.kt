package com.mariomanzano.nasaexplorer.ui.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mariomanzano.domain.Error
import com.mariomanzano.domain.entities.NasaItem
import com.mariomanzano.nasaexplorer.network.toError
import com.mariomanzano.nasaexplorer.usecases.GetFavoritesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
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
                .flowOn(Dispatchers.IO)
                .onStart { _state.update { it.copy(loading = true) } }
                .catch { cause ->
                    _state.update {
                        it.copy(
                            loading = false,
                            error = cause.toError()
                        )
                    }
                }
                .onEach { flow ->
                    flow.first { true }.also { items ->
                        favoriteList.addAll(items.sortedBy { it.type })
                        favoriteList
                    }
                }
                .onCompletion {
                    if (favoriteList.isEmpty()) {
                        _state.update {
                            it.copy(loading = false, error = Error.NoData)
                        }
                    } else {
                        _state.update {
                            it.copy(loading = false, items = favoriteList)
                        }
                    }
                }.collect()
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