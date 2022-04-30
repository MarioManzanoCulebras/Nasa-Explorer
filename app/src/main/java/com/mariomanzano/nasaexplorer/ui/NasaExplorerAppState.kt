package com.mariomanzano.nasaexplorer.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.mariomanzano.nasaexplorer.ui.NasaExplorerAppState.Companion.BOTTOM_NAV_OPTIONS
import com.mariomanzano.nasaexplorer.ui.navigation.NavItem
import com.mariomanzano.nasaexplorer.ui.screens.common.startIndex

@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun rememberNasaExplorerAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberNavController(),
    pagerState: PagerState = rememberPagerState(initialPage = startIndex),
    scrollState: Map<Int, LazyListState> = BOTTOM_NAV_OPTIONS.associate { it.ordinal to rememberLazyListState() }
): NasaExplorerAppState = remember(scaffoldState, navController, pagerState, scrollState) {
    NasaExplorerAppState(scaffoldState, navController, pagerState, scrollState)
}

@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
class NasaExplorerAppState(
    val scaffoldState: ScaffoldState,
    val navController: NavHostController,
    val pagerState: PagerState,
    val scrollState: Map<Int, LazyListState>,
) {
    companion object {
        val BOTTOM_NAV_OPTIONS =
            listOf(NavItem.Pictures, NavItem.Earth, NavItem.Mars, NavItem.Favorites)
    }

    private var lastScrollIndex = BOTTOM_NAV_OPTIONS.associate { it.ordinal to 0 }.toMutableMap()

    private val _scrollUp = BOTTOM_NAV_OPTIONS.associate { it.ordinal to MutableLiveData(false) }

    @Composable
    fun getScrollUpState(pageIndex: Int): MutableLiveData<Boolean> {
        return _scrollUp[pageIndex] ?: MutableLiveData(false)
    }

    fun updateScrollPosition(newScrollIndex: Int, pageIndex: Int) {
        if (newScrollIndex == lastScrollIndex[pageIndex]) return

        _scrollUp[pageIndex]?.value = newScrollIndex > lastScrollIndex[pageIndex] ?: 0
        lastScrollIndex[pageIndex] = newScrollIndex
    }

    fun forceScrollUpSimulation(pageIndex: Int) {
        _scrollUp[pageIndex]?.value = true
    }
}