package com.mariomanzano.nasaexplorer.ui.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mariomanzano.domain.Error
import com.mariomanzano.domain.entities.NasaItem
import com.mariomanzano.nasaexplorer.network.toError
import com.mariomanzano.nasaexplorer.usecases.GetFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavoritesUseCase: GetFavoritesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()

    private val favoriteList: MutableList<NasaItem> = mutableListOf()

    fun getFavorites() {
        viewModelScope.launch {
            getFavoritesUseCase()
                .flowOn(Dispatchers.IO)
                .onStart {
                    favoriteList.clear()
                    _state.update {
                        it.copy(loading = true, items = favoriteList)
                    }
                }
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
                            it.copy(
                                loading = false,
                                items = favoriteList.sortedByDescending { favorite -> favorite.date })
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