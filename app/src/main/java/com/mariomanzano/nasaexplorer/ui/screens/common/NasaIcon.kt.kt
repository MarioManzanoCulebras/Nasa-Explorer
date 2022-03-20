package com.mariomanzano.nasaexplorer.ui.screens.common

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.mariomanzano.nasaexplorer.R

sealed class NasaIcon(val resourceId: Int, val modifier: Modifier = Modifier) {
    object Spacecraft : NasaIcon(R.drawable.ic_spacecraft, modifier = Modifier
        .padding(top = 30.dp)
        .graphicsLayer {
            scaleX = 1f
            scaleY = 1f
        })

    object RocketVertical : NasaIcon(R.drawable.ic_rocket, modifier = Modifier
        .padding(top = 25.dp)
        .graphicsLayer {
            scaleX = 0.7f
            scaleY = 0.7f
        })

    object Earth : NasaIcon(R.drawable.ic_earth, modifier = Modifier.graphicsLayer {
        scaleX = 1.0f
        scaleY = 1.0f
    })

    object NasaLogo : NasaIcon(R.drawable.ic_nasa_logo)

    object FavoriteOn : NasaIcon(R.drawable.ic_favorite_on)

    object FavoriteOff : NasaIcon(R.drawable.ic_favorite_off)
}