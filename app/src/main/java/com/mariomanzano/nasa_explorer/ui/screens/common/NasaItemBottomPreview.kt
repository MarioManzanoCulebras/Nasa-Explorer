package com.mariomanzano.nasa_explorer.ui.screens.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.mariomanzano.nasa_explorer.data.entities.NasaItem
import com.mariomanzano.nasa_explorer.R
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun <T : NasaItem> NasaItemBottomPreview(item: T?, onGoToDetail: (T) -> Unit) {
    if (item != null) {
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                painter = rememberImagePainter(item.url),
                contentDescription = item.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(96.dp)
                    .aspectRatio(1 / 1.5f)
            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(item.date.time))
                Text(text = item.title?:"", style = MaterialTheme.typography.h6)
                Text(text = item.description?:"")
                Button(
                    onClick = { onGoToDetail(item) },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(text = stringResource(id = R.string.go_to_detail))
                }
            }
        }
    } else {
        // It needs something for when the item is null, or the Bottom Sheet will crash in this case
        Spacer(modifier = Modifier.height(1.dp))
    }
}