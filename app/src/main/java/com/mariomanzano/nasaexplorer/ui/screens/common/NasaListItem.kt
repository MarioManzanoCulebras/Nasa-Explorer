package com.mariomanzano.nasaexplorer.ui.screens.common

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.mariomanzano.domain.entities.NasaItem
import com.mariomanzano.nasaexplorer.R

@Composable
fun <T : NasaItem> NasaListItem(
    nasaItem: T,
    onItemMore: (T) -> Unit,
    showFooterInside: Boolean,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(
        modifier = modifier.padding(8.dp)
    ) {
        Card {
            Box {
                if (nasaItem.mediaType == "image") {
                    NasaImageWithLoader(urlImage = nasaItem.url)
                } else {
                    IconButton(
                        onClick = { openYoutubeWithUrl(nasaItem.url, context) },
                        modifier = Modifier
                            .fillMaxSize()
                            .aspectRatio(1f)
                            .background(
                                Brush.verticalGradient(
                                    listOf(
                                        MaterialTheme.colors.primaryVariant,
                                        MaterialTheme.colors.secondary
                                    )
                                )
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = ""
                        )
                    }
                }
                if (showFooterInside) {
                    Surface(
                        color = colorResource(R.color.grey_with_alpha),
                        modifier = Modifier.align(Alignment.BottomEnd)
                    ) {
                        FooterRow(nasaItem = nasaItem, onItemMore = onItemMore)
                    }
                }

                if (nasaItem.favorite) BuildIcon(
                    icon = Icons.Default.Favorite,
                    nasaIcon = NasaIcon.FavoriteOn
                )
            }
        }
        if (!showFooterInside) {
            FooterRow(nasaItem = nasaItem, onItemMore = onItemMore)
        }
    }
}

@Composable
fun <T : NasaItem> FooterRow(nasaItem: T, onItemMore: (T) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable {
            onItemMore(nasaItem)
        }
    ) {
        Text(
            text = nasaItem.title ?: "",
            style = MaterialTheme.typography.subtitle1,
            maxLines = 2,
            modifier = Modifier
                .padding(8.dp, 16.dp)
                .weight(1f)
        )
        IconButton(onClick = { onItemMore(nasaItem) }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = stringResource(id = R.string.more_options)
            )
        }
    }
}

fun openYoutubeWithUrl(url: String?, context: Context) {
    val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
        setPackage("com.google.android.youtube")
    }
    val webIntent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse(url)
    )
    try {
        startActivity(context, appIntent, null)
    } catch (ex: ActivityNotFoundException) {
        startActivity(context, webIntent, null)
    }
}