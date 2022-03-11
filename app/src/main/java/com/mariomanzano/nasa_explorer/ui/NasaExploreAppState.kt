package com.mariomanzano.nasa_explorer.ui

import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mariomanzano.nasa_explorer.ui.navigation.NavItem
import com.mariomanzano.nasa_explorer.ui.navigation.navigatePoppingUpToStartDestination
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun rememberMarvelAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
): NasaExploreAppState = remember(scaffoldState, navController, coroutineScope) {
    NasaExploreAppState(scaffoldState, navController, coroutineScope)
}

class NasaExploreAppState(
    val scaffoldState: ScaffoldState,
    val navController: NavHostController,
    private val coroutineScope: CoroutineScope,
) {
    companion object {
        val DRAWER_OPTIONS = listOf(NavItem.HOME, NavItem.NASA_LIBRARY)
        val BOTTOM_NAV_OPTIONS = listOf(NavItem.DAILY_PICTURES, NavItem.EARTH, NavItem.MARS)
    }

    val currentRoute: String
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination?.route
            ?: ""

    val showUpNavigation: Boolean
        @Composable get() = !NavItem.values().map { it.navCommand.route }.contains(currentRoute)

    val showBottomNavigation: Boolean
        @Composable get() = BOTTOM_NAV_OPTIONS.any { currentRoute.contains(it.navCommand.feature.route) }

    val drawerSelectedIndex: Int
        @Composable get() = if (showBottomNavigation) {
            DRAWER_OPTIONS.indexOf(NavItem.HOME)
        } else {
            DRAWER_OPTIONS.indexOfFirst { it.navCommand.route == currentRoute }
        }

    fun onUpClick() {
        navController.popBackStack()
    }

    fun onNavItemClick(navItem: NavItem) {
        navController.navigatePoppingUpToStartDestination(navItem.navCommand.route)
    }

    fun onDrawerOptionClick(navItem: NavItem) {
        coroutineScope.launch { scaffoldState.drawerState.close() }
        onNavItemClick(navItem)
    }

    fun onMenuClick() {
        coroutineScope.launch { scaffoldState.drawerState.open() }
    }
}