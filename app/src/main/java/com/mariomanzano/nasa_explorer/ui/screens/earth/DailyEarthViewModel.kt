package com.mariomanzano.nasa_explorer.ui.screens.earth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.right
import com.mariomanzano.nasa_explorer.data.entities.EarthItem
import com.mariomanzano.nasa_explorer.data.entities.Result
import com.mariomanzano.nasa_explorer.data.repositories.DailyEarthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DailyEarthViewModel : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            _state.value = UiState(dailyPictures = DailyEarthRepository.get())
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val dailyPictures: Result<List<EarthItem>> = emptyList<EarthItem>().right()
    )
}