package com.mariomanzano.nasa_explorer.ui.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.mariomanzano.nasa_explorer.data.database.NasaRoomDataSource
import com.mariomanzano.nasa_explorer.data.repositories.DailyEarthRepository
import com.mariomanzano.nasa_explorer.data.repositories.DailyPicturesRepository
import com.mariomanzano.nasa_explorer.data.repositories.MarsRepository
import com.mariomanzano.nasa_explorer.network.NasaServerDataSource
import com.mariomanzano.nasa_explorer.ui.common.app
import com.mariomanzano.nasa_explorer.ui.screens.dailypicture.DailyPictureDetailScreen
import com.mariomanzano.nasa_explorer.ui.screens.dailypicture.DailyPictureScreen
import com.mariomanzano.nasa_explorer.ui.screens.earth.EarthDetailScreen
import com.mariomanzano.nasa_explorer.ui.screens.earth.EarthScreen
import com.mariomanzano.nasa_explorer.ui.screens.mars.MarsDetailScreen
import com.mariomanzano.nasa_explorer.ui.screens.mars.MarsScreen

@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun Navigation(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Feature.DAILY_PICTURE.route
    ) {
        dailyPictureNav(navController)
        earthNav(navController)
        marsNav(navController)
    }
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
private fun NavGraphBuilder.dailyPictureNav(navController: NavController) {
    navigation(
        startDestination = NavCommand.ContentType(Feature.DAILY_PICTURE).route,
        route = Feature.DAILY_PICTURE.route
    ) {
        composable(NavCommand.ContentType(Feature.DAILY_PICTURE)) {
            //Todo: revisar doble creación - con Hilt se arreglará
            val application = LocalContext.current.applicationContext.app
            val repository = DailyPicturesRepository(
                NasaRoomDataSource(application.db.nasaDao()),
                NasaServerDataSource()
            )

            DailyPictureScreen(
                onClick = { pictureOfTheDay ->
                    navController.navigate(
                        NavCommand.ContentTypeDetail(Feature.DAILY_PICTURE).createRoute(
                            pictureOfTheDay.id
                        )
                    )
                },
                repository
            )
        }

        composable(NavCommand.ContentTypeDetail(Feature.DAILY_PICTURE)) {
            //Todo: revisar doble creación - con Hilt se arreglará
            val application = LocalContext.current.applicationContext.app
            val repository = DailyPicturesRepository(
                NasaRoomDataSource(application.db.nasaDao()),
                NasaServerDataSource()
            )
            DailyPictureDetailScreen(it.arguments?.getInt("itemId") ?: 0, repository)
        }
    }
}

@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
private fun NavGraphBuilder.earthNav(navController: NavController) {
    navigation(
        startDestination = NavCommand.ContentType(Feature.EARTH).route,
        route = Feature.EARTH.route
    ) {
        composable(NavCommand.ContentType(Feature.EARTH)) {
            //Todo: revisar doble creación - con Hilt se arreglará
            val application = LocalContext.current.applicationContext.app
            val repository = DailyEarthRepository(
                NasaRoomDataSource(application.db.nasaDao()),
                NasaServerDataSource()
            )

            EarthScreen(
                onClick = { earthDay ->
                    navController.navigate(
                        NavCommand.ContentTypeDetail(Feature.EARTH).createRoute(
                            earthDay.id
                        )
                    )
                },
                repository
            )
        }

        composable(NavCommand.ContentTypeDetail(Feature.EARTH)) {
            //Todo: revisar doble creación - con Hilt se arreglará
            val application = LocalContext.current.applicationContext.app
            val repository = DailyEarthRepository(
                NasaRoomDataSource(application.db.nasaDao()),
                NasaServerDataSource()
            )
            EarthDetailScreen(it.arguments?.getInt("itemId") ?: 0, repository)
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
private fun NavGraphBuilder.marsNav(navController: NavController) {
    navigation(
        startDestination = NavCommand.ContentType(Feature.MARS).route,
        route = Feature.MARS.route
    ) {
        composable(NavCommand.ContentType(Feature.MARS)) {
            //Todo: revisar doble creación - con Hilt se arreglará
            val application = LocalContext.current.applicationContext.app
            val repository = MarsRepository(
                NasaRoomDataSource(application.db.nasaDao()),
                NasaServerDataSource()
            )

            MarsScreen(
                onClick = { marsItem ->
                    navController.navigate(
                        NavCommand.ContentTypeDetail(Feature.MARS).createRoute(
                            marsItem.id
                        )
                    )
                },
                repository
            )
        }

        composable(NavCommand.ContentTypeDetail(Feature.MARS)) {
            //Todo: revisar doble creación - con Hilt se arreglará
            val application = LocalContext.current.applicationContext.app
            val repository = MarsRepository(
                NasaRoomDataSource(application.db.nasaDao()),
                NasaServerDataSource()
            )

            MarsDetailScreen(it.arguments?.getInt("itemId") ?: 0, repository)
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