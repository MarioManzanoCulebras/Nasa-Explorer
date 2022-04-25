package com.mariomanzano.nasaexplorer.ui.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.mariomanzano.nasaexplorer.ui.screens.common.NasaContainerScreen
import com.mariomanzano.nasaexplorer.ui.screens.dailypicture.DailyPictureDetailScreen
import com.mariomanzano.nasaexplorer.ui.screens.dailypicture.DailyPictureScreen
import com.mariomanzano.nasaexplorer.ui.screens.earth.EarthDetailScreen
import com.mariomanzano.nasaexplorer.ui.screens.earth.EarthScreen
import com.mariomanzano.nasaexplorer.ui.screens.favorites.FavoritesDetailScreen
import com.mariomanzano.nasaexplorer.ui.screens.favorites.FavoritesScreen
import com.mariomanzano.nasaexplorer.ui.screens.mars.MarsDetailScreen
import com.mariomanzano.nasaexplorer.ui.screens.mars.MarsScreen

const val CONTAINER = "Container"

@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun Navigation(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = CONTAINER
    ) {
        composable(CONTAINER) {
            NasaContainerScreen(navController = navController)
        }
        composable(NavCommand.ContentTypeDetailById(Feature.DAILY_PICTURE)) {
            DailyPictureDetailScreen()
        }
        composable(NavCommand.ContentTypeDetailById(Feature.EARTH)) {
            EarthDetailScreen()
        }
        composable(NavCommand.ContentTypeDetailById(Feature.MARS)) {
            MarsDetailScreen()
        }
        composable(NavCommand.ContentTypeDetailByIdAndType(Feature.FAVORITES)) {
            FavoritesDetailScreen()
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
fun NavGraphBuilder.dailyPictureNav(navController: NavController) {
    navigation(
        startDestination = NavCommand.ContentType(Feature.DAILY_PICTURE).route,
        route = Feature.DAILY_PICTURE.route
    ) {
        composable(NavCommand.ContentType(Feature.DAILY_PICTURE)) {
            DailyPictureScreen(
                onClick = { pictureOfTheDay ->
                    navController.navigate(
                        NavCommand.ContentTypeDetailById(Feature.DAILY_PICTURE).createRoute(
                            pictureOfTheDay.id
                        )
                    )
                }
            )
        }

        composable(NavCommand.ContentTypeDetailById(Feature.DAILY_PICTURE)) {
            DailyPictureDetailScreen()
        }
    }
}

@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
fun NavGraphBuilder.earthNav(navController: NavController) {
    navigation(
        startDestination = NavCommand.ContentType(Feature.EARTH).route,
        route = Feature.EARTH.route
    ) {
        composable(NavCommand.ContentType(Feature.EARTH)) {
            EarthScreen(
                onClick = { earthDay ->
                    navController.navigate(
                        NavCommand.ContentTypeDetailById(Feature.EARTH).createRoute(
                            earthDay.id
                        )
                    )
                }
            )
        }

        composable(NavCommand.ContentTypeDetailById(Feature.EARTH)) {
            EarthDetailScreen()
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
fun NavGraphBuilder.marsNav(navController: NavController) {
    navigation(
        startDestination = NavCommand.ContentType(Feature.MARS).route,
        route = Feature.MARS.route
    ) {
        composable(NavCommand.ContentType(Feature.MARS)) {
            MarsScreen(
                onClick = { marsItem ->
                    navController.navigate(
                        NavCommand.ContentTypeDetailById(Feature.MARS).createRoute(
                            marsItem.id
                        )
                    )
                }
            )
        }

        composable(NavCommand.ContentTypeDetailById(Feature.MARS)) {
            MarsDetailScreen()
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
fun NavGraphBuilder.favoritesNav(navController: NavController) {
    navigation(
        startDestination = NavCommand.ContentType(Feature.FAVORITES).route,
        route = Feature.FAVORITES.route
    ) {
        composable(NavCommand.ContentType(Feature.FAVORITES)) {
            FavoritesScreen(
                onClick = { nasaItem ->
                    navController.navigate(
                        NavCommand.ContentTypeDetailByIdAndType(Feature.FAVORITES)
                            .createRoute(nasaItem.id, nasaItem.type)
                    )
                }
            )
        }

        composable(NavCommand.ContentTypeDetailByIdAndType(Feature.FAVORITES)) {
            FavoritesDetailScreen()
        }
    }
}

private fun NavGraphBuilder.composable(
    navCommand: NavCommand,
    content: @Composable (NavBackStackEntry) -> Unit
) {
    composable(
        route = navCommand.route,
        arguments = navCommand.args
    ) {
        content(it)
    }
}