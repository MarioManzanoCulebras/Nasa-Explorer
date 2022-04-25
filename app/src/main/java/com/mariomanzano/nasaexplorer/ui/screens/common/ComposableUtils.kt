package com.mariomanzano.nasaexplorer.ui.screens.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun BuildIcon(
    icon: ImageVector,
    nasaIcon: NasaIcon? = null,
    title: String? = null,
    colorFilter: ColorFilter? = null
) {
    if (nasaIcon != null) {
        Image(
            painter = painterResource(nasaIcon.resourceId),
            contentDescription = title,
            colorFilter = colorFilter
        )
    } else {
        Icon(imageVector = icon, contentDescription = title)
    }
}

@Composable
fun NasaImageWithLoader(
    modifier: Modifier? = null, urlImage: String?,
    contentScale: ContentScale = ContentScale.Crop,
    contentDescription: String? = null
) {
    GlideImage(
        imageModel = urlImage,
        contentScale = contentScale,
        contentDescription = contentDescription,
        modifier = modifier ?: Modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        loading = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .placeholder(
                        visible = true,
                        color = Color.LightGray,
                        highlight = PlaceholderHighlight.shimmer(
                            highlightColor = Color.White
                        )
                    )
                    .aspectRatio(1f)
            )
        },
        failure = {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painterResource(NasaIcon.NasaLogo.resourceId),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    text = "Image Not Found",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

        })
}