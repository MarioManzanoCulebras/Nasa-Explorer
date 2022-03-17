package com.mariomanzano.nasaexplorer.ui.screens.earth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mariomanzano.domain.Error
import com.mariomanzano.domain.entities.EarthItem
import com.mariomanzano.nasaexplorer.network.toError
import com.mariomanzano.nasaexplorer.usecases.FindEarthUseCase
import com.mariomanzano.nasaexplorer.usecases.SwitchItemToFavoriteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DailyEarthDetailViewModel(
    itemId: Int,
    findEarthUseCase: FindEarthUseCase,
    private val favoriteUseCase: SwitchItemToFavoriteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            findEarthUseCase(itemId)
                .catch { cause -> _state.update { it.copy(error = cause.toError()) } }
                .collect { earthItem -> _state.update { UiState(earthPicture = earthItem) } }
        }
    }

    fun onFavoriteClicked() {
        viewModelScope.launch {
            _state.value.earthPicture?.let { item ->
                val error = favoriteUseCase(item)
                _state.update { it.copy(error = error) }
            }
        }
    }

    data class UiState(
        val earthPicture: EarthItem? = null,
        val error: Error? = null
    )
}

@Suppress("UNCHECKED_CAST")
class DailyEarthDetailViewModelFactory(
    private val itemId: Int,
    private val findEarthUseCase: FindEarthUseCase,
    private val favoriteUseCase: SwitchItemToFavoriteUseCase
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DailyEarthDetailViewModel(itemId, findEarthUseCase, favoriteUseCase) as T
    }
}