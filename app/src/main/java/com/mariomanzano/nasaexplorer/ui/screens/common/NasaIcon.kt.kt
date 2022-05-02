package com.mariomanzano.nasaexplorer.ui.screens.common

import com.mariomanzano.nasaexplorer.R

sealed class NasaIcon(val resourceId: Int) {
    object Spacecraft : NasaIcon(R.drawable.ic_spacecraft)

    object RocketVertical : NasaIcon(R.drawable.ic_rocket)

    object Earth : NasaIcon(R.drawable.ic_earth)

    object NasaLogo : NasaIcon(R.drawable.ic_nasa_logo)

    object FavoriteMenu : NasaIcon(R.drawable.ic_favorite_off)

    object FavoriteOn : NasaIcon(R.drawable.ic_favorite_on_light)

    object FavoriteOff : NasaIcon(R.drawable.ic_favorite_off_light)
}