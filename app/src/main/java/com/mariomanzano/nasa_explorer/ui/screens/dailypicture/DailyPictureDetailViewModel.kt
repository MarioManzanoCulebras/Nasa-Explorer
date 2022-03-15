package com.mariomanzano.nasa_explorer.ui.screens.dailypicture

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mariomanzano.nasa_explorer.data.entities.Error
import com.mariomanzano.nasa_explorer.data.entities.PictureOfDayItem
import com.mariomanzano.nasa_explorer.data.entities.toError
import com.mariomanzano.nasa_explorer.usecases.FindPODUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DailyPictureDetailViewModel(
    itemId: Int,
    findPODUseCase: FindPODUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            findPODUseCase(itemId)
                .catch { cause -> _state.update { it.copy(error = cause.toError()) } }
                .collect { dailyPicture -> _state.update { UiState(dailyPicture = dailyPicture) } }
        }
    }

    data class UiState(
        val dailyPicture: PictureOfDayItem? = null,
        val error: Error? = null
    )
}

@Suppress("UNCHECKED_CAST")
class DailyPictureDetailViewModelFactory(
    private val itemId: Int,
    private val findPODUseCase: FindPODUseCase
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DailyPictureDetailViewModel(itemId, findPODUseCase) as T
    }
}