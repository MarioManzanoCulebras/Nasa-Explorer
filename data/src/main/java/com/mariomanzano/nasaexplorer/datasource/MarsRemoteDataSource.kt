package com.mariomanzano.nasaexplorer.datasource

import arrow.core.Either
import com.mariomanzano.domain.Error
import com.mariomanzano.domain.entities.MarsItem

interface MarsRemoteDataSource {
    suspend fun findMarsItems(): Either<Error, List<MarsItem>>
}