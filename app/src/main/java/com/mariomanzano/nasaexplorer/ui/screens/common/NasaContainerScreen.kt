package com.mariomanzano.nasaexplorer.ui.screens.common

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.mariomanzano.nasaexplorer.ui.navigation.Feature
import com.mariomanzano.nasaexplorer.ui.navigation.NavCommand
import com.mariomanzano.nasaexplorer.ui.navigation.NavItem
import com.mariomanzano.nasaexplorer.ui.screens.dailypicture.DailyPictureScreen
import com.mariomanzano.nasaexplorer.ui.screens.earth.EarthScreen
import com.mariomanzano.nasaexplorer.ui.screens.favorites.FavoritesScreen
import com.mariomanzano.nasaexplorer.ui.screens.mars.MarsScreen
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun NasaContainerScreen(
    navController: NavHostController
) {
    val list = NavItem.values().toList()
    val pagerState = rememberPagerState()
    Scaffold(
        bottomBar = {
            TabRow(
                pagerState = pagerState,
                list = list
            )
        }
    ) {
        Column {
            HorizontalPager(
                count = list.size,
                state = pagerState
            ) { page ->
                when (page) {
                    0 -> {
                        DailyPictureScreen(
                            onClick = { pictureOfTheDay ->
                                navController.navigate(
                                    NavCommand.ContentTypeDetailById(Feature.DAILY_PICTURE)
                                        .createRoute(
                                            pictureOfTheDay.id
                                        )
                                )
                            }
                        )
                    }
                    1 -> {
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
                    2 -> {
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
                    3 -> {
                        FavoritesScreen(
                            onClick = { nasaItem ->
                                navController.navigate(
                                    NavCommand.ContentTypeDetailByIdAndType(Feature.FAVORITES)
                                        .createRoute(nasaItem.id, nasaItem.type)
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

@ExperimentalPagerApi
@Composable
private fun TabRow(
    pagerState: PagerState,
    list: List<NavItem>
) {
    val scope = rememberCoroutineScope()

    TabRow(
        backgroundColor = MaterialTheme.colors.primary,
        selectedTabIndex = pagerState.currentPage,
        indicator = {}
    ) {
        list.forEach {
            val selected = it.ordinal == pagerState.currentPage
            Tab(
                selected = selected,
                onClick = { scope.launch { pagerState.animateScrollToPage(it.ordinal) } },
                text = {
                    Text(
                        text = it.name,
                        fontSize = 11.sp,
                        color = if (selected) Color.White else Color.Black
                    )
                },
                icon = {
                    BuildIcon(
                        icon = it.icon ?: Icons.Default.Flight,
                        nasaIcon = it.nasaIcon,
                        title = stringResource(it.title ?: 0),
                        colorFilter = ColorFilter.tint(if (selected) Color.White else Color.Black)
                    )
                }
            )
        }
    }
}