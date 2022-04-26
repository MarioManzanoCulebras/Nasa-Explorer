package com.mariomanzano.nasaexplorer.ui.common

import android.content.Context
import com.mariomanzano.nasaexplorer.App

val Context.app: App
    get() = applicationContext as App

fun Int.floorMod(other: Int): Int = when (other) {
    0 -> this
    else -> this - floorDiv(other) * other
}