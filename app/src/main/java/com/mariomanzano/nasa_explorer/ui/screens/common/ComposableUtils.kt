package com.mariomanzano.nasa_explorer.ui.screens.common

import androidx.compose.foundation.Image
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource

@Composable
fun BuildIcon(
    icon: ImageVector,
    modifier: Modifier = Modifier,
    nasaIcon: NasaIcon? = null,
    title: String? = null
) {
    if (nasaIcon != null) {
        Image(
            painter = painterResource(nasaIcon.resourceId),
            modifier = nasaIcon.modifier,
            contentDescription = title
        )
    } else {
        Icon(imageVector = icon, modifier = modifier, contentDescription = title)
    }
}