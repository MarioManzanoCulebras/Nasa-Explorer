package com.mariomanzano.nasa_explorer.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.mariomanzano.nasa_explorer.R

enum class NavItem(
    val navCommand: NavCommand,
    val icon: ImageVector,
    @StringRes val title: Int
) {
    HOME(NavCommand.ContentType(Feature.DAILY_PICTURE), Icons.Default.Launch, R.string.home),
    NASA_LIBRARY(NavCommand.ContentType(Feature.NASA_LIBRARY), Icons.Default.Satellite, R.string.nasa_library),
    DAILY_PICTURE(NavCommand.ContentType(Feature.DAILY_PICTURE), Icons.Default.Face, R.string.daily_picture),
    MARS(NavCommand.ContentType(Feature.MARS), Icons.Default.TravelExplore, R.string.mars),
    EARTH(NavCommand.ContentType(Feature.EARTH), Icons.Default.Public, R.string.earth)
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