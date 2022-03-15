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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.mariomanzano.nasa_explorer.R
import com.mariomanzano.nasa_explorer.data.entities.MarsItem
import com.mariomanzano.nasa_explorer.data.entities.Result

@ExperimentalMaterialApi
@Composable
fun MarsItemDetailScreen(loading: Boolean, marsItem: Result<MarsItem?>) {

    if (loading) {
        CircularProgressIndicator()
    }

    marsItem.fold({ ErrorMessage(it) }) { item ->
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
                        Header(marsItem = item)
                    }
                }
            }
        }
    }
}

@Composable
private fun Header(marsItem: MarsItem) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = rememberImagePainter(marsItem.url),
            contentDescription = marsItem.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .aspectRatio(1f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = DateFormatter.Simple.formatter.format(marsItem.date.time),
            textAlign = TextAlign.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 10.dp)
        )
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
        marsItem.roverLandingDate?.time?.let {
            Text(
                text = stringResource(id = R.string.rover_landing_date) + DateFormatter.Simple.formatter.format(
                    it
                ),
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(16.dp, 0.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        marsItem.roverLaunchingDate?.time?.let {
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
        Spacer(modifier = Modifier.height(32.dp))
    }
}
