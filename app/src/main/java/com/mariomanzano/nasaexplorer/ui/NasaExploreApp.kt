package com.mariomanzano.nasaexplorer.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mariomanzano.nasaexplorer.ui.navigation.Navigation
import com.mariomanzano.nasaexplorer.ui.theme.NasaExplorerTheme

@ExperimentalPagerApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun NasaExploreApp(appState: NasaExplorerAppState = rememberNasaExplorerAppState()) {
    NasaExploreScreen {
        Scaffold { padding ->
            Box(modifier = Modifier.padding(padding)) {
                Navigation(appState)
            }
        }
        SetStatusAndNavigationBarsColorEffect()
    }
}

@Composable
private fun SetStatusAndNavigationBarsColorEffect(
    color: Color = MaterialTheme.colors.background,
    systemUiController: SystemUiController = rememberSystemUiController()
) {

    SideEffect {
        systemUiController.setStatusBarColor(color)
        systemUiController.setNavigationBarColor(color)
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