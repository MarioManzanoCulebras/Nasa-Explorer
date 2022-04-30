package com.mariomanzano.nasaexplorer.ui.screens.common

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flight
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.mariomanzano.nasaexplorer.ui.NasaExplorerAppState
import com.mariomanzano.nasaexplorer.ui.NasaExplorerAppState.Companion.BOTTOM_NAV_OPTIONS
import com.mariomanzano.nasaexplorer.ui.common.floorMod
import com.mariomanzano.nasaexplorer.ui.navigation.Feature
import com.mariomanzano.nasaexplorer.ui.navigation.NavCommand
import com.mariomanzano.nasaexplorer.ui.navigation.NavItem
import com.mariomanzano.nasaexplorer.ui.navigation.navigatePoppingUpToStartDestination
import com.mariomanzano.nasaexplorer.ui.screens.dailypicture.DailyPictureScreen
import com.mariomanzano.nasaexplorer.ui.screens.earth.EarthScreen
import com.mariomanzano.nasaexplorer.ui.screens.favorites.FavoritesScreen
import com.mariomanzano.nasaexplorer.ui.screens.mars.MarsScreen
import kotlinx.coroutines.launch

const val startIndex = Int.MAX_VALUE / 2

@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun NasaContainerScreen(
    appState: NasaExplorerAppState
) {
    val pageIndex = (appState.pagerState.currentPage - startIndex).floorMod(BOTTOM_NAV_OPTIONS.size)

    Scaffold(
        bottomBar = {
            ScrollableTabRow(
                pagerState = appState.pagerState,
                list = BOTTOM_NAV_OPTIONS,
                scrollUpState = appState.getScrollUpState(pageIndex = pageIndex).observeAsState(),
                pageIndex = pageIndex
            )
        },
        scaffoldState = appState.scaffoldState
    ) {
        Column {
            HorizontalPager(
                count = Int.MAX_VALUE,
                state = appState.pagerState
            ) { index ->
                when ((index - startIndex).floorMod(BOTTOM_NAV_OPTIONS.size)) {
                    0 -> {
                        PagerContainer(appState = appState, pageNumber = NavItem.Pictures.ordinal) {
                            DailyPictureScreen(
                                onClick = { pictureOfTheDay ->
                                    appState.navController.navigatePoppingUpToStartDestination(
                                        NavCommand.ContentTypeDetailById(Feature.DAILY_PICTURE)
                                            .createRoute(
                                                pictureOfTheDay.id
                                            )
                                    )
                                },
                                listState = appState.scrollState[NavItem.Pictures.ordinal]
                                    ?: rememberLazyListState(),
                                onItemsMoreClicked = { appState.forceScrollUpSimulation(NavItem.Pictures.ordinal) }
                            )
                        }
                    }
                    1 -> {
                        PagerContainer(appState = appState, pageNumber = NavItem.Earth.ordinal) {
                            EarthScreen(
                                onClick = { earthDay ->
                                    appState.navController.navigatePoppingUpToStartDestination(
                                        NavCommand.ContentTypeDetailById(Feature.EARTH).createRoute(
                                            earthDay.id
                                        )
                                    )
                                },
                                listState = appState.scrollState[NavItem.Earth.ordinal]
                                    ?: rememberLazyListState(),
                                onItemsMoreClicked = { appState.forceScrollUpSimulation(NavItem.Earth.ordinal) }
                            )
                        }
                    }
                    2 -> {
                        PagerContainer(appState = appState, pageNumber = NavItem.Mars.ordinal) {
                            MarsScreen(
                                onClick = { marsItem ->
                                    appState.navController.navigatePoppingUpToStartDestination(
                                        NavCommand.ContentTypeDetailById(Feature.MARS).createRoute(
                                            marsItem.id
                                        )
                                    )
                                },
                                listState = appState.scrollState[NavItem.Mars.ordinal]
                                    ?: rememberLazyListState(),
                                onItemsMoreClicked = { appState.forceScrollUpSimulation(NavItem.Mars.ordinal) }
                            )
                        }
                    }
                    3 -> {
                        PagerContainer(
                            appState = appState,
                            pageNumber = NavItem.Favorites.ordinal
                        ) {
                            FavoritesScreen(
                                onClick = { nasaItem ->
                                    appState.navController.navigatePoppingUpToStartDestination(
                                        NavCommand.ContentTypeDetailByIdAndType(Feature.FAVORITES)
                                            .createRoute(nasaItem.id, nasaItem.type)
                                    )
                                },
                                listState = appState.scrollState[NavItem.Favorites.ordinal]
                                    ?: rememberLazyListState(),
                                onItemsMoreClicked = { appState.forceScrollUpSimulation(NavItem.Favorites.ordinal) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
private fun PagerContainer(
    appState: NasaExplorerAppState,
    pageNumber: Int,
    content: @Composable () -> Unit
) {
    LaunchedEffect(appState.scrollState[pageNumber]?.firstVisibleItemIndex) {
        appState.updateScrollPosition(
            appState.scrollState[pageNumber]?.firstVisibleItemIndex ?: 0,
            pageNumber
        )
    }
    content()
}


@ExperimentalPagerApi
@Composable
private fun ScrollableTabRow(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    list: List<NavItem>,
    scrollUpState: State<Boolean?>,
    pageIndex: Int
) {
    val position by animateFloatAsState(
        targetValue = if (scrollUpState.value == true) 200f else 0f,
        animationSpec = spring(stiffness = Spring.StiffnessLow)
    )

    Surface(modifier = modifier
        .graphicsLayer { translationY = (position) }, elevation = 8.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        )
        TabRow(
            pagerState = pagerState,
            list = list,
            pageIndex = pageIndex
        )
    }
}

@ExperimentalPagerApi
@Composable
private fun TabRow(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    list: List<NavItem>,
    pageIndex: Int
) {
    val scope = rememberCoroutineScope()
    TabRow(
        modifier = modifier,
        backgroundColor = MaterialTheme.colors.primary,
        selectedTabIndex = pagerState.currentPage,
        indicator = {}
    ) {
        list.forEach {
            val selected = (it.ordinal == pageIndex)
            Tab(
                selected = selected,
                onClick = {
                    scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + it.ordinal - pageIndex) }
                },
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