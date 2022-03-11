package com.mariomanzano.nasa_explorer.ui.navigation

import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.mariomanzano.nasa_explorer.ui.screens.common.BuildIcon
import com.mariomanzano.nasa_explorer.ui.screens.common.NasaIcon

@Composable
fun AppBottomNavigation(
    bottomNavOptions: List<NavItem>,
    currentRoute: String,
    onNavItemClick: (NavItem) -> Unit
) {
    NasaBottomNavigation {
        bottomNavOptions.forEach { item ->
            val title = stringResource(item.title?:0)

            BottomNavigationItem(
                selected = currentRoute.contains(item.navCommand.feature.route),
                icon = {
                    BuildIcon(icon = item.icon?: Icons.Default.Flight, nasaIcon = item.nasaIcon, title = title)
                },
                label = { Text(text = title) },
                onClick = { onNavItemClick(item) }
            )
        }
    }
}