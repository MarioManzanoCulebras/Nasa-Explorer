package com.mariomanzano.nasaexplorer.ui.screens.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mariomanzano.domain.entities.MarsItem
import com.mariomanzano.domain.entities.NasaItem
import com.mariomanzano.domain.entities.PictureOfDayItem
import com.mariomanzano.nasaexplorer.R

@ExperimentalMaterialApi
@Composable
fun NasaItemDetailScreen(nasaItem: NasaItem?, onFavoriteClick: () -> Unit = {}) {
    nasaItem?.let {
        NasaItemDetailScaffold(
            nasaItem = it,
            onFavoriteClick = onFavoriteClick
        ) { padding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(padding)
            ) {
                item {
                    Header(nasaItem = it)
                }
            }
        }
    }
}

@Composable
private fun Header(nasaItem: NasaItem) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
    ) {
        if (nasaItem.mediaType == "image") {
            NasaImageWithLoader(
                urlImage = nasaItem.url,
                contentScale = ContentScale.Fit,
                contentDescription = nasaItem.title
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
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = if (nasaItem is PictureOfDayItem) DateFormatter.Simple.formatter.format(nasaItem.date.time)
            else DateFormatter.FullTime.formatter.format(nasaItem.date.time),
            textAlign = TextAlign.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 10.dp)
        )
        if (nasaItem !is MarsItem) {
            Text(
                text = nasaItem.title ?: "",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h4,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            SelectionContainer {
                Text(
                    text = buildAnnotatedString {
                        append(
                            AnnotatedString(
                                text = nasaItem.description ?: "",
                                paragraphStyle = ParagraphStyle(
                                    textIndent = TextIndent(
                                        firstLine = 25.sp,
                                        restLine = 10.sp
                                    )
                                )
                            )
                        )
                    },
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(16.dp, 0.dp)
                )
            }
            Spacer(modifier = Modifier.height(100.dp))
        } else {
            ExtraMarsHeader(marsItem = nasaItem)
        }
    }
}

@Composable
private fun ExtraMarsHeader(marsItem: MarsItem) {
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        text = stringResource(id = R.string.sun_martian_rotation) + marsItem.sun,
        style = MaterialTheme.typography.body1,
        modifier = Modifier.padding(16.dp, 0.dp)
    )
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        text = stringResource(id = R.string.camera_name) + marsItem.cameraName,
        style = MaterialTheme.typography.body1,
        modifier = Modifier.padding(16.dp, 0.dp)
    )
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        text = stringResource(id = R.string.rover_name) + marsItem.roverName,
        style = MaterialTheme.typography.body1,
        modifier = Modifier.padding(16.dp, 0.dp)
    )
    Spacer(modifier = Modifier.height(16.dp))
    marsItem.roverLandingDate.time.let {
        Text(
            text = stringResource(id = R.string.rover_landing_date) + DateFormatter.Simple.formatter.format(
                it
            ),
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(16.dp, 0.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
    marsItem.roverLaunchingDate.time.let {
        Text(
            text = stringResource(id = R.string.rover_launching_date) + DateFormatter.Simple.formatter.format(
                it
            ),
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(16.dp, 0.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
    Text(
        text = stringResource(id = R.string.rover_mission_status) + marsItem.roverMissionStatus,
        style = MaterialTheme.typography.body1,
        modifier = Modifier.padding(16.dp, 0.dp)
    )
    Spacer(modifier = Modifier.height(100.dp))
}
