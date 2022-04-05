package com.mariomanzano.nasaexplorer.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mariomanzano.nasaexplorer.data.utils.checkIfDayAfterToday
import com.mariomanzano.nasaexplorer.usecases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getLastPODUpdateDateUseCase: GetLastPODUpdateDateUseCase,
    private val getLastEarthUpdateDateNeedUseCase: GetLastEarthUpdateDateNeedUseCase,
    private val getLastMarsUpdateDateUseCase: GetLastMarsUpdateDateUseCase,
    private val updateLastPODUpdateUseCase: UpdateLastPODUpdateUseCase,
    private val updateLastEarthUpdateUseCase: UpdateLastEarthUpdateUseCase,
    private val updateLastMarsUpdateUseCase: UpdateLastMarsUpdateUseCase
) : ViewModel() {

    fun updateDates() {
        viewModelScope.launch {
            getLastPODUpdateDateUseCase()
                .collect { info ->
                    if (info?.date?.checkIfDayAfterToday() == true) {
                        updateLastPODUpdateUseCase(info.apply {
                            date = Calendar.getInstance()
                            updateNeed = true
                        })
                    }
                }
        }
        viewModelScope.launch {
            getLastEarthUpdateDateNeedUseCase()
                .collect { info ->
                    if (info?.date?.checkIfDayAfterToday() == true) {
                        updateLastEarthUpdateUseCase(info.apply {
                            date = Calendar.getInstance()
                            updateNeed = true
                        })
                    }
                }
        }
        viewModelScope.launch {
            getLastMarsUpdateDateUseCase()
                .collect { info ->
                    if (info?.date?.checkIfDayAfterToday() == true) {
                        updateLastMarsUpdateUseCase(info.apply {
                            date = Calendar.getInstance()
                            updateNeed = true
                        })
                    }
                }
        }
    }

}