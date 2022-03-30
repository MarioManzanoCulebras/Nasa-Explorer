package com.mariomanzano.nasaexplorer.ui.screens.common

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import coil.compose.rememberImagePainter
import com.mariomanzano.domain.entities.NasaItem
import com.mariomanzano.nasaexplorer.R


@Composable
fun <T : NasaItem> NasaListItem(
    nasaItem: T,
    onItemMore: (T) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(
        modifier = modifier.padding(8.dp)
    ) {
        Card {
            if (nasaItem.mediaType == "image") {
                Image(
                    painter = rememberImagePainter(data = nasaItem.url),
                    contentDescription = nasaItem.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.LightGray)
                        .aspectRatio(1f)
                )
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
                        contentDescription = "",
                        modifier = Modifier.align(
                            Alignment.CenterHorizontally
                        )
                    )
                }
            }


            if (nasaItem.favorite) BuildIcon(
                icon = Icons.Default.Favorite,
                nasaIcon = NasaIcon.FavoriteOn
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = nasaItem.title?:"",
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
}

fun openYoutubeWithUrl(url: String?, context: Context) {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(url)
    intent.setPackage("com.google.android.youtube")
    startActivity(context, intent, null)
    //Todo: Check if youtube not installed start the browser youtube
}