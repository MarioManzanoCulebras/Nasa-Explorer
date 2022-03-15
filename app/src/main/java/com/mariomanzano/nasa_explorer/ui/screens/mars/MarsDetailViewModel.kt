package com.mariomanzano.nasa_explorer.ui.screens.mars

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.mariomanzano.nasa_explorer.data.entities.MarsItem
import com.mariomanzano.nasa_explorer.data.entities.Result
import com.mariomanzano.nasa_explorer.data.repositories.MarsRepository
import com.mariomanzano.nasa_explorer.ui.navigation.NavArg
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*

class MarsDetailViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val idTimeSelected = savedStateHandle.get<Long>(NavArg.ItemId.key) ?: 0L

    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            _state.value = UiState(
                marsItem = MarsRepository.find(
                    Calendar.getInstance().apply { timeInMillis = idTimeSelected })
            )
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val marsItem: Result<MarsItem?> = Either.Right(null)
    )
}