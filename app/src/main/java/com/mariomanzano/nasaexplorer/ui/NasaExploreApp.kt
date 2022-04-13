package com.mariomanzano.nasaexplorer.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Flight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mariomanzano.nasaexplorer.R
import com.mariomanzano.nasaexplorer.ui.navigation.*
import com.mariomanzano.nasaexplorer.ui.screens.common.BuildIcon
import com.mariomanzano.nasaexplorer.ui.screens.common.NasaIcon
import com.mariomanzano.nasaexplorer.ui.theme.NasaExplorerTheme

@ExperimentalPagerApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun NasaExploreApp(appState: NasaExploreAppState = rememberMarvelAppState()) {
    NasaExploreScreen {
        Scaffold(
            topBar = {
                NasaExplorerTopAppBar(
                    title = { Text(stringResource(id = R.string.app_name)) },
                    navigationIcon = {
                        if (appState.showUpNavigation) {
                            AppBarIcon(
                                imageVector = Icons.Default.ArrowBack,
                                onClick = { appState.onUpClick() })
                        } else {
                            IconButton(onClick = { appState.onMenuClick() }) {
                                BuildIcon(icon = Icons.Default.Flight, nasaIcon = NasaIcon.NasaLogo)
                            }
                        }
                    }
                )
            },
            bottomBar = {
                if (appState.showBottomNavigation) {
                    AppBottomNavigation(
                        bottomNavOptions = NasaExploreAppState.BOTTOM_NAV_OPTIONS,
                        currentRoute = appState.currentRoute,
                        onNavItemClick = { appState.onNavItemClick(it) })
                }
            },
            drawerContent = {
                DrawerContent(
                    drawerOptions = NasaExploreAppState.DRAWER_OPTIONS,
                    selectedIndex = appState.drawerSelectedIndex,
                    onOptionClick = { appState.onDrawerOptionClick(it) }
                )
            },
            scaffoldState = appState.scaffoldState
        ) { padding ->
            Box(modifier = Modifier.padding(padding)) {
                Navigation(appState.navController)
            }
        }

        SetStatusBarColorEffect()
    }
}

@Composable
fun NasaExploreScreen(content: @Composable () -> Unit) {
    NasaExplorerTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
            content()
        }
    }
}

@Composable
private fun SetStatusBarColorEffect(
    color: Color = MaterialTheme.colors.primary,
    systemUiController: SystemUiController = rememberSystemUiController()
) {

    SideEffect {
        systemUiController.setSystemBarsColor(color)
    }
}