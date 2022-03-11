package com.mariomanzano.nasa_explorer.ui.screens.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.mariomanzano.nasa_explorer.data.entities.NasaItem
import com.mariomanzano.nasa_explorer.data.entities.Result
import java.text.SimpleDateFormat
import java.util.*

@ExperimentalMaterialApi
@Composable
fun NasaItemDetailScreen(loading: Boolean, nasaItem: Result<NasaItem?>) {

    if (loading) {
        CircularProgressIndicator()
    }

    nasaItem.fold({ ErrorMessage(it) }) { item ->
        if (item != null) {
            NasaItemDetailScaffold(
                nasaItem = item
            ) { padding ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(padding)
                ) {
                    item {
                        Header(nasaItem = item)
                    }
                }
            }
        }
    }
}

@Composable
private fun Header(nasaItem: NasaItem) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = rememberImagePainter(nasaItem.url),
            contentDescription = nasaItem.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .aspectRatio(1f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(nasaItem.date.time),
            textAlign = TextAlign.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 10.dp)
        )
        Text(
            text = nasaItem.title?:"",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h4,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = nasaItem.description?:"",
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(16.dp, 0.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}
