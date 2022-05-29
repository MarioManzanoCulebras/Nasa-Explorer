package com.mariomanzano.nasaexplorer.ui.screens.common

import android.content.Context
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ShareCompat
import com.mariomanzano.domain.entities.NasaItem

@ExperimentalMaterialApi
@Composable
fun NasaItemDetailScaffold(
    nasaItem: NasaItem,
    onFavoriteClick: () -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        floatingActionButton = {
            Row {
                Spacer(modifier = Modifier.width(12.dp))
                FloatingActionButton(
                    onClick = { shareNasaItem(context, nasaItem.title ?: "", nasaItem.url ?: "") }
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        tint = Color.Companion.White,
                        contentDescription = null
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                FloatingActionButton(onClick = onFavoriteClick) {
                    if (nasaItem.favorite) BuildIcon(
                        icon = Icons.Default.Favorite,
                        nasaIcon = NasaIcon.FavoriteOn
                    )
                    else BuildIcon(
                        icon = Icons.Default.FavoriteBorder,
                        nasaIcon = NasaIcon.FavoriteOff
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        content = content
    )
}

private fun shareNasaItem(context: Context, name: String, url: String) {
    val intent = ShareCompat
        .IntentBuilder(context)
        .setType("text/plain")
        .setSubject(name)
        .setText(url)
        .intent
    context.startActivity(intent)
}