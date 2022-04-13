package com.mariomanzano.nasaexplorer.ui.navigation

import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flight
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import com.mariomanzano.nasaexplorer.ui.screens.common.BuildIcon

@Composable
fun AppBottomNavigation(
    bottomNavOptions: List<NavItem>,
    currentRoute: String,
    onNavItemClick: (NavItem) -> Unit
) {
    NasaBottomNavigation {
        bottomNavOptions.forEach { item ->
            val title = stringResource(item.title ?: 0)
            val selected = currentRoute.contains(item.navCommand.feature.route)

            BottomNavigationItem(
                selected = selected,
                selectedContentColor = Color.White,
                unselectedContentColor = Color.Black,
                icon = {
                    BuildIcon(
                        icon = item.icon ?: Icons.Default.Flight,
                        nasaIcon = item.nasaIcon,
                        title = title,
                        colorFilter = ColorFilter.tint(if (selected) Color.White else Color.Black)
                    )
                },
                label = { Text(text = title) },
                onClick = { onNavItemClick(item) }
            )
        }
    }
}