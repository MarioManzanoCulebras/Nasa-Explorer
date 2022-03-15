package com.mariomanzano.nasa_explorer.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.mariomanzano.nasa_explorer.R
import com.mariomanzano.nasa_explorer.ui.screens.common.NasaIcon

enum class NavItem(
    val navCommand: NavCommand,
    val icon: ImageVector? = null,
    val nasaIcon: NasaIcon? = null,
    @StringRes val title: Int?
) {
    HOME(NavCommand.ContentType(Feature.DAILY_PICTURE), Icons.Default.Home, title = R.string.home),
    NASA_LIBRARY(NavCommand.ContentType(Feature.NASA_LIBRARY), nasaIcon = NasaIcon.NasaLogo, title = R.string.nasa_library),
    DAILY_PICTURES(NavCommand.ContentType(Feature.DAILY_PICTURE), nasaIcon = NasaIcon.Spacecraft, title = R.string.daily_pictures),
    MARS(NavCommand.ContentType(Feature.MARS), nasaIcon = NasaIcon.RocketVertical, title = R.string.mars),
    EARTH(NavCommand.ContentType(Feature.EARTH), nasaIcon = NasaIcon.Earth, title = R.string.earth)
}

sealed class NavCommand(
    internal val feature: Feature,
    internal val subRoute: String = "home",
    private val navArgs: List<NavArg> = emptyList()
) {
    class ContentType(feature: Feature) : NavCommand(feature)

    class ContentTypeDetail(feature: Feature) :
        NavCommand(feature, "detail", listOf(NavArg.ItemId)) {
        fun createRoute(itemId: Int) = "${feature.route}/$subRoute/$itemId"
    }

    val route = run {
        val argValues = navArgs.map { "{${it.key}}" }
        listOf(feature.route)
            .plus(subRoute)
            .plus(argValues)
            .joinToString("/")
    }

    val args = navArgs.map {
        navArgument(it.key) { type = it.navType }
    }

}

enum class NavArg(val key: String, val navType: NavType<*>) {
    ItemId("itemId", NavType.IntType)
}