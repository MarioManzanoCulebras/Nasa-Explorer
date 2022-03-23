package com.mariomanzano.nasaexplorer.ui.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mariomanzano.domain.Error
import com.mariomanzano.domain.entities.NasaItem
import com.mariomanzano.nasaexplorer.network.toError
import com.mariomanzano.nasaexplorer.usecases.FindFavoriteUseCase
import com.mariomanzano.nasaexplorer.usecases.SwitchFavoriteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavoriteDetailViewModel(
    itemId: Int,
    itemType: String,
    private val findFavoriteUseCase: FindFavoriteUseCase,
    private val switchFavoriteUseCase: SwitchFavoriteUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            findFavoriteUseCase(itemId, itemType)
                .catch { cause -> _state.update { it.copy(error = cause.toError()) } }
                .collect { item -> _state.update { UiState(nasaItem = item) } }
        }
    }

    fun onFavoriteClicked() {
        viewModelScope.launch {
            _state.value.nasaItem?.let { item ->
                val error = switchFavoriteUseCase(
                    item.id,
                    item.type,
                    !item.favorite
                )
                _state.update { it.copy(error = error) }
            }
        }
    }

    data class UiState(
        val nasaItem: NasaItem? = null,
        val error: Error? = null
    )
}

@Suppress("UNCHECKED_CAST")
class FavoriteDetailViewModelFactory(
    private val itemId: Int,
    private val itemType: String,
    private val findFavoriteUseCase: FindFavoriteUseCase,
    private val switchFavoriteUseCase: SwitchFavoriteUseCase
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavoriteDetailViewModel(
            itemId,
            itemType,
            findFavoriteUseCase,
            switchFavoriteUseCase
        ) as T
    }
}