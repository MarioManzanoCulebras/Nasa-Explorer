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
        val character = cache.find {
            it.date.get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH) &&
                    it.date.get(Calendar.MONTH) == date.get(Calendar.MONTH) &&
                    it.date.get(Calendar.YEAR) == date.get(Calendar.YEAR) &&
                    it.date.get(Calendar.HOUR) == date.get(Calendar.HOUR) &&
                    it.date.get(Calendar.MINUTE) == date.get(Calendar.MINUTE) &&
                    it.date.get(Calendar.SECOND) == date.get(Calendar.SECOND)
        }
        (character ?: findActionRemote())
    }

}