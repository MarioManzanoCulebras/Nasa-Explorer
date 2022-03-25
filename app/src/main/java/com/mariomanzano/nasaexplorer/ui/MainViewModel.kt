package com.mariomanzano.nasaexplorer.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mariomanzano.domain.entities.LastUpdateInfo
import com.mariomanzano.nasaexplorer.data.utils.checkIfDayAfter
import com.mariomanzano.nasaexplorer.usecases.*
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel(
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
                    if (info == null || info.date.checkIfDayAfter()) {
                        val infoNotNull = info ?: LastUpdateInfo(0, Calendar.getInstance(), true)
                        updateLastPODUpdateUseCase(infoNotNull.apply { updateNeed = true })
                    }
                }
        }
        viewModelScope.launch {
            getLastEarthUpdateDateNeedUseCase()
                .collect { info ->
                    if (info == null || info.date.checkIfDayAfter()) {
                        val infoNotNull = info ?: LastUpdateInfo(0, Calendar.getInstance(), true)
                        updateLastEarthUpdateUseCase(infoNotNull.apply { updateNeed = true })
                    }
                }
        }
        viewModelScope.launch {
            getLastMarsUpdateDateUseCase()
                .collect { info ->
                    if (info == null || info.date.checkIfDayAfter()) {
                        val infoNotNull = info ?: LastUpdateInfo(0, Calendar.getInstance(), true)
                        updateLastMarsUpdateUseCase(infoNotNull.apply { updateNeed = true })
                    }
                }
        }
    }

}