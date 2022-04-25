package com.mariomanzano.nasaexplorer.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.mariomanzano.nasaexplorer.R
import com.mariomanzano.nasaexplorer.ui.screens.common.NasaIcon

enum class NavItem(
    val navCommand: NavCommand,
    val icon: ImageVector? = null,
    val nasaIcon: NasaIcon? = null,
    @StringRes val title: Int?
) {
    Pictures(
        NavCommand.ContentType(Feature.DAILY_PICTURE),
        nasaIcon = NasaIcon.Spacecraft,
        title = R.string.daily_pictures
    ),
    Earth(NavCommand.ContentType(Feature.EARTH), nasaIcon = NasaIcon.Earth, title = R.string.earth),
    Mars(
        NavCommand.ContentType(Feature.MARS),
        nasaIcon = NasaIcon.RocketVertical,
        title = R.string.mars
    ),
    Favorites(
        NavCommand.ContentType(Feature.FAVORITES),
        nasaIcon = NasaIcon.FavoriteOn,
        title = R.string.favorites
    )
}

sealed class NavCommand(
    internal val feature: Feature,
    internal val subRoute: String = "home",
    private val navArgs: List<NavArg> = emptyList()
) {
    class ContentType(feature: Feature) : NavCommand(feature)

    class ContentTypeDetailById(feature: Feature) :
        NavCommand(feature, "detail", listOf(NavArg.ItemId)) {
        fun createRoute(itemId: Int) = "${feature.route}/$subRoute/$itemId"
    }

    class ContentTypeDetailByIdAndType(feature: Feature) :
        NavCommand(feature, "favoriteDetail", listOf(NavArg.ItemId, NavArg.ItemType)) {
        fun createRoute(itemId: Int, itemType: String) =
            "${feature.route}/$subRoute/$itemId/$itemType"
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
    ItemId("itemId", NavType.IntType),
    ItemType("itemType", NavType.StringType)
}