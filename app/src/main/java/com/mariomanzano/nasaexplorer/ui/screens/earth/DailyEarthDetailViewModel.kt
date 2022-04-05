package com.mariomanzano.nasaexplorer.ui.screens.earth

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mariomanzano.domain.Error
import com.mariomanzano.domain.entities.EarthItem
import com.mariomanzano.nasaexplorer.network.toError
import com.mariomanzano.nasaexplorer.ui.navigation.NavArg
import com.mariomanzano.nasaexplorer.usecases.FindEarthUseCase
import com.mariomanzano.nasaexplorer.usecases.SwitchItemToFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyEarthDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    findEarthUseCase: FindEarthUseCase,
    private val favoriteUseCase: SwitchItemToFavoriteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            findEarthUseCase(savedStateHandle.get<Int>(NavArg.ItemId.key) ?: 0)
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