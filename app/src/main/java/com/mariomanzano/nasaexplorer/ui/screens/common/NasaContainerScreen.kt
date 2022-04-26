package com.mariomanzano.nasaexplorer.ui.screens.common

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.mariomanzano.nasaexplorer.ui.common.floorMod
import com.mariomanzano.nasaexplorer.ui.navigation.Feature
import com.mariomanzano.nasaexplorer.ui.navigation.NavCommand
import com.mariomanzano.nasaexplorer.ui.navigation.NavItem
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
    navController: NavHostController,
    viewModel: NasaContainerViewModel = hiltViewModel()
) {
    val list = NavItem.values().toList()
    val pagerState = rememberPagerState(initialPage = startIndex)


    val scrollUpState = viewModel.scrollUp.observeAsState()

    Scaffold(
        bottomBar = {
            ScrollableTabRow(
                pagerState = pagerState,
                list = list,
                scrollUpState = scrollUpState
            )
        }
    ) {
        Column {
            HorizontalPager(
                count = Int.MAX_VALUE,
                state = pagerState
            ) { index ->
                when ((index - startIndex).floorMod(list.size)) {
                    0 -> {
                        val scrollState = rememberLazyListState()
                        viewModel.updateScrollPosition(scrollState.firstVisibleItemIndex)

                        DailyPictureScreen(
                            onClick = { pictureOfTheDay ->
                                navController.navigate(
                                    NavCommand.ContentTypeDetailById(Feature.DAILY_PICTURE)
                                        .createRoute(
                                            pictureOfTheDay.id
                                        )
                                )
                            },
                            listState = scrollState
                        )
                    }
                    1 -> {
                        val scrollState = rememberLazyListState()
                        viewModel.updateScrollPosition(scrollState.firstVisibleItemIndex)

                        EarthScreen(
                            onClick = { earthDay ->
                                navController.navigate(
                                    NavCommand.ContentTypeDetailById(Feature.EARTH).createRoute(
                                        earthDay.id
                                    )
                                )
                            },
                            listState = scrollState
                        )
                    }
                    2 -> {
                        val scrollState = rememberLazyListState()
                        viewModel.updateScrollPosition(scrollState.firstVisibleItemIndex)

                        MarsScreen(
                            onClick = { marsItem ->
                                navController.navigate(
                                    NavCommand.ContentTypeDetailById(Feature.MARS).createRoute(
                                        marsItem.id
                                    )
                                )
                            },
                            listState = scrollState
                        )
                    }
                    3 -> {
                        val scrollState = rememberLazyListState()
                        viewModel.updateScrollPosition(scrollState.firstVisibleItemIndex)

                        FavoritesScreen(
                            onClick = { nasaItem ->
                                navController.navigate(
                                    NavCommand.ContentTypeDetailByIdAndType(Feature.FAVORITES)
                                        .createRoute(nasaItem.id, nasaItem.type)
                                )
                            },
                            listState = scrollState
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
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    list: List<NavItem>
) {
    val scope = rememberCoroutineScope()
    val pageIndex = (pagerState.currentPage - startIndex).floorMod(list.size)
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

@ExperimentalPagerApi
@Composable
private fun ScrollableTabRow(
    background: Color = MaterialTheme.colors.primary,
    pagerState: PagerState,
    list: List<NavItem>,
    scrollUpState: State<Boolean?>
) {
    val position by animateFloatAsState(
        targetValue = if (scrollUpState.value == true) 200f else 0f,
        animationSpec = spring(stiffness = Spring.StiffnessLow)
    )

    Surface(modifier = Modifier.graphicsLayer { translationY = (position) }, elevation = 8.dp) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(color = background),
        )
        TabRow(
            pagerState = pagerState,
            list = list
        )
    }
}