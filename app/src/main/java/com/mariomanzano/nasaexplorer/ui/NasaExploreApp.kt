package com.mariomanzano.nasaexplorer.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.mariomanzano.nasaexplorer.ui.navigation.Navigation
import com.mariomanzano.nasaexplorer.ui.theme.NasaExplorerTheme

@ExperimentalPagerApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun NasaExploreApp() {
    NasaExploreScreen {
        Scaffold { padding ->
            Box(modifier = Modifier.padding(padding)) {
                Navigation(rememberNavController())
            }
        }
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