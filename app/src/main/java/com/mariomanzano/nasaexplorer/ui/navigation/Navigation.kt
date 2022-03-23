package com.mariomanzano.nasaexplorer.ui.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.mariomanzano.nasaexplorer.data.database.NasaRoomDataSource
import com.mariomanzano.nasaexplorer.network.NasaServerDataSource
import com.mariomanzano.nasaexplorer.repositories.*
import com.mariomanzano.nasaexplorer.ui.common.app
import com.mariomanzano.nasaexplorer.ui.screens.dailypicture.DailyPictureDetailScreen
import com.mariomanzano.nasaexplorer.ui.screens.dailypicture.DailyPictureScreen
import com.mariomanzano.nasaexplorer.ui.screens.earth.EarthDetailScreen
import com.mariomanzano.nasaexplorer.ui.screens.earth.EarthScreen
import com.mariomanzano.nasaexplorer.ui.screens.favorites.FavoritesDetailScreen
import com.mariomanzano.nasaexplorer.ui.screens.favorites.FavoritesScreen
import com.mariomanzano.nasaexplorer.ui.screens.mars.MarsDetailScreen
import com.mariomanzano.nasaexplorer.ui.screens.mars.MarsScreen

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
        favoritesNav(navController)
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
            val nasaRoomDataSource = NasaRoomDataSource(application.db.nasaDao())
            val dailyPicturesRepository = DailyPicturesRepository(
                nasaRoomDataSource,
                NasaServerDataSource()
            )
            val lastDbUpdateRepository = LastDbUpdateRepository(nasaRoomDataSource)

            DailyPictureScreen(
                onClick = { pictureOfTheDay ->
                    navController.navigate(
                        NavCommand.ContentTypeDetailById(Feature.DAILY_PICTURE).createRoute(
                            pictureOfTheDay.id
                        )
                    )
                },
                dailyPicturesRepository,
                lastDbUpdateRepository
            )
        }

        composable(NavCommand.ContentTypeDetailById(Feature.DAILY_PICTURE)) {
            //Todo: revisar doble creación - con Hilt se arreglará
            val application = LocalContext.current.applicationContext.app
            val dailyPicturesRepository = DailyPicturesRepository(
                NasaRoomDataSource(application.db.nasaDao()),
                NasaServerDataSource()
            )
            val earthRepository = DailyEarthRepository(
                NasaRoomDataSource(application.db.nasaDao()),
                NasaServerDataSource()
            )
            val marsRepository = MarsRepository(
                NasaRoomDataSource(application.db.nasaDao()),
                NasaServerDataSource()
            )
            DailyPictureDetailScreen(
                it.arguments?.getInt("itemId") ?: 0,
                dailyPicturesRepository,
                earthRepository,
                marsRepository
            )
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
            val nasaRoomDataSource = NasaRoomDataSource(application.db.nasaDao())
            val dailyEarthRepository = DailyEarthRepository(
                nasaRoomDataSource,
                NasaServerDataSource()
            )
            val lastDbUpdateRepository = LastDbUpdateRepository(nasaRoomDataSource)

            EarthScreen(
                onClick = { earthDay ->
                    navController.navigate(
                        NavCommand.ContentTypeDetailById(Feature.EARTH).createRoute(
                            earthDay.id
                        )
                    )
                },
                dailyEarthRepository,
                lastDbUpdateRepository
            )
        }

        composable(NavCommand.ContentTypeDetailById(Feature.EARTH)) {
            //Todo: revisar doble creación - con Hilt se arreglará
            val application = LocalContext.current.applicationContext.app
            val dailyPicturesRepository = DailyPicturesRepository(
                NasaRoomDataSource(application.db.nasaDao()),
                NasaServerDataSource()
            )
            val earthRepository = DailyEarthRepository(
                NasaRoomDataSource(application.db.nasaDao()),
                NasaServerDataSource()
            )
            val marsRepository = MarsRepository(
                NasaRoomDataSource(application.db.nasaDao()),
                NasaServerDataSource()
            )
            EarthDetailScreen(
                it.arguments?.getInt("itemId") ?: 0,
                dailyPicturesRepository,
                earthRepository,
                marsRepository
            )
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
            val nasaRoomDataSource = NasaRoomDataSource(application.db.nasaDao())
            val marsRepository = MarsRepository(
                nasaRoomDataSource,
                NasaServerDataSource()
            )
            val lastDbUpdateRepository = LastDbUpdateRepository(nasaRoomDataSource)

            MarsScreen(
                onClick = { marsItem ->
                    navController.navigate(
                        NavCommand.ContentTypeDetailById(Feature.MARS).createRoute(
                            marsItem.id
                        )
                    )
                },
                marsRepository,
                lastDbUpdateRepository
            )
        }

        composable(NavCommand.ContentTypeDetailById(Feature.MARS)) {
            //Todo: revisar doble creación - con Hilt se arreglará
            val application = LocalContext.current.applicationContext.app
            val dailyPicturesRepository = DailyPicturesRepository(
                NasaRoomDataSource(application.db.nasaDao()),
                NasaServerDataSource()
            )
            val earthRepository = DailyEarthRepository(
                NasaRoomDataSource(application.db.nasaDao()),
                NasaServerDataSource()
            )
            val marsRepository = MarsRepository(
                NasaRoomDataSource(application.db.nasaDao()),
                NasaServerDataSource()
            )

            MarsDetailScreen(
                it.arguments?.getInt("itemId") ?: 0,
                dailyPicturesRepository,
                earthRepository,
                marsRepository
            )
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
private fun NavGraphBuilder.favoritesNav(navController: NavController) {
    navigation(
        startDestination = NavCommand.ContentType(Feature.FAVORITES).route,
        route = Feature.FAVORITES.route
    ) {
        composable(NavCommand.ContentType(Feature.FAVORITES)) {
            //Todo: revisar doble creación - con Hilt se arreglará
            val application = LocalContext.current.applicationContext.app
            val nasaRoomDataSource = NasaRoomDataSource(application.db.nasaDao())
            val favoritesRepository =
                FavoritesRepository(nasaRoomDataSource, nasaRoomDataSource, nasaRoomDataSource)

            FavoritesScreen(
                onClick = { nasaItem ->
                    navController.navigate(
                        NavCommand.ContentTypeDetailByIdAndType(Feature.FAVORITES)
                            .createRoute(nasaItem.id, nasaItem.type)
                    )
                },
                favoritesRepository
            )
        }

        composable(NavCommand.ContentTypeDetailByIdAndType(Feature.FAVORITES)) {
            //Todo: revisar doble creación - con Hilt se arreglará
            val application = LocalContext.current.applicationContext.app
            val nasaRoomDataSource = NasaRoomDataSource(application.db.nasaDao())
            val favoritesRepository =
                FavoritesRepository(nasaRoomDataSource, nasaRoomDataSource, nasaRoomDataSource)

            FavoritesDetailScreen(
                it.arguments?.getInt("itemId") ?: 0,
                it.arguments?.getString("itemType") ?: "",
                favoritesRepository
            )
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