package com.mariomanzano.nasa_explorer.ui.screens.earth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mariomanzano.nasa_explorer.data.entities.EarthItem
import com.mariomanzano.nasa_explorer.data.entities.Error
import com.mariomanzano.nasa_explorer.data.entities.toError
import com.mariomanzano.nasa_explorer.usecases.FindEarthUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DailyEarthDetailViewModel(
    itemId: Int,
    findEarthUseCase: FindEarthUseCase
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

    data class UiState(
        val earthPicture: EarthItem? = null,
        val error: Error? = null
    )
}

@Suppress("UNCHECKED_CAST")
class DailyEarthDetailViewModelFactory(
    private val itemId: Int,
    private val findEarthUseCase: FindEarthUseCase
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DailyEarthDetailViewModel(itemId, findEarthUseCase) as T
    }
}