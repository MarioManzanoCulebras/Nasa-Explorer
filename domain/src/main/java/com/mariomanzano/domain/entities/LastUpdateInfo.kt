package com.mariomanzano.domain.entities

import java.util.*

data class LastUpdateInfo(
    val id: Int,
    val date: Calendar,
    var updateNeed: Boolean
)
