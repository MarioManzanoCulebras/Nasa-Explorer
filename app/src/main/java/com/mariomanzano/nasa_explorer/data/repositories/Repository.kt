package com.mariomanzano.nasa_explorer.data.repositories

import com.mariomanzano.nasa_explorer.data.entities.NasaItem
import com.mariomanzano.nasa_explorer.data.entities.Result
import com.mariomanzano.nasa_explorer.data.entities.tryCall
import java.util.*

abstract class Repository<T : NasaItem> {

    private var cache: List<T> = emptyList()

    internal suspend fun get(getAction: suspend () -> List<T>): Result<List<T>> = tryCall {
        if (cache.isEmpty()) {
            cache = getAction()
        }
        cache
    }

    internal suspend fun find(
        date: Calendar,
        findActionRemote: suspend () -> T
    ): Result<T> = tryCall {
        (getFromCache(date) ?: findActionRemote())
    }

    private fun getFromCache(date: Calendar) = cache.find {
        nasaItemComparison(date, it.date)
    }

    private fun nasaItemComparison(date1: Calendar, date2: Calendar) =
        date1.get(Calendar.DAY_OF_MONTH) == date2.get(Calendar.DAY_OF_MONTH) &&
                date1.get(Calendar.MONTH) == date2.get(Calendar.MONTH) &&
                date1.get(Calendar.YEAR) == date2.get(Calendar.YEAR) &&
                date1.get(Calendar.HOUR) == date2.get(Calendar.HOUR) &&
                date1.get(Calendar.MINUTE) == date2.get(Calendar.MINUTE) &&
                date1.get(Calendar.SECOND) == date2.get(Calendar.SECOND)

}