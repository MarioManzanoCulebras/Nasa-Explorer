package com.mariomanzano.nasa_explorer.ui.screens.common

import android.content.Context
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ShareCompat
import com.mariomanzano.nasa_explorer.data.entities.NasaItem
import com.mariomanzano.nasa_explorer.ui.navigation.AppBarIcon

@ExperimentalMaterialApi
@Composable
fun NasaItemDetailScaffold(
    nasaItem: NasaItem,
    content: @Composable (PaddingValues) -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { shareCharacter(context, nasaItem.title?:"", nasaItem.url?:"") }
            ) {
                Icon(imageVector = Icons.Default.Share, contentDescription = null)
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        bottomBar = {
            BottomAppBar(
                cutoutShape = CircleShape
            ) {
                AppBarIcon(imageVector = Icons.Default.Menu, onClick = { })
                Spacer(modifier = Modifier.weight(1f))
                AppBarIcon(imageVector = Icons.Default.Favorite, onClick = { })
            }
        },
        content = content
    )
}

private fun shareCharacter(context: Context, name: String, url: String) {
    val intent = ShareCompat
        .IntentBuilder(context)
        .setType("text/plain")
        .setSubject(name)
        .setText(url)
        .intent
    context.startActivity(intent)
}