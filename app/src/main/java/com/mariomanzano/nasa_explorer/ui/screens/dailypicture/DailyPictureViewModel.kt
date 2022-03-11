package com.mariomanzano.nasa_explorer.ui.screens.dailypicture

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.right
import com.mariomanzano.nasa_explorer.data.entities.PictureOfDayItem
import com.mariomanzano.nasa_explorer.data.entities.Result
import com.mariomanzano.nasa_explorer.data.repositories.DailyPicturesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DailyPictureViewModel : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            _state.value = UiState(dailyPictures = DailyPicturesRepository.get())
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val dailyPictures: Result<List<PictureOfDayItem>> = emptyList<PictureOfDayItem>().right()
    )
}