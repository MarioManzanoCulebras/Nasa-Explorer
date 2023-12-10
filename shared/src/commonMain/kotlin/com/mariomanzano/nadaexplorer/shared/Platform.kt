package com.mariomanzano.nadaexplorer.shared

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform