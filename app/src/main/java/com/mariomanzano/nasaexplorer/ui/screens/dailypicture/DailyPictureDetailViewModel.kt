package com.mariomanzano.nasaexplorer.ui.screens.dailypicture

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mariomanzano.domain.Error
import com.mariomanzano.domain.entities.PictureOfDayItem
import com.mariomanzano.nasaexplorer.network.toError
import com.mariomanzano.nasaexplorer.ui.navigation.NavArg
import com.mariomanzano.nasaexplorer.usecases.FindPODUseCase
import com.mariomanzano.nasaexplorer.usecases.SwitchItemToFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyPictureDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    findPODUseCase: FindPODUseCase,
    private val favoriteUseCase: SwitchItemToFavoriteUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            findPODUseCase(savedStateHandle.get<Int>(NavArg.ItemId.key) ?: 0)
                .catch { cause -> _state.update { it.copy(error = cause.toError()) } }
                .collect { dailyPicture -> _state.update { UiState(dailyPicture = dailyPicture) } }
        }
    }

    fun onFavoriteClicked() {
        viewModelScope.launch {
            _state.value.dailyPicture?.let { item ->
                val error = favoriteUseCase(item)
                _state.update { it.copy(error = error) }
            }
        }
    }

    data class UiState(
        val dailyPicture: PictureOfDayItem? = null,
        val error: Error? = null
    )
}