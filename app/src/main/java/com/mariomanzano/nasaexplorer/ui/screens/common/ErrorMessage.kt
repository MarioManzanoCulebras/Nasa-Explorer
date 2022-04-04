package com.mariomanzano.nasaexplorer.ui.screens.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mariomanzano.domain.Error
import com.mariomanzano.nasaexplorer.ui.NasaExploreScreen

@Composable
fun ErrorMessage(
    error: Error,
    onClickRetry: (() -> Unit)? = null,
) {
    val message = when (error) {
        Error.Connectivity -> "Connectivity Error"
        is Error.Server -> "Server Error: ${error.code}"
        is Error.NoData -> "No Data"
        is Error.Unknown -> "Unknown Error: ${error.message}"
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = if (error is Error.NoData) Icons.Default.Info else Icons.Default.Warning,
            contentDescription = message,
            modifier = Modifier.size(128.dp),
            tint = if (error is Error.NoData) MaterialTheme.colors.primary else MaterialTheme.colors.error
        )
        Text(
            text = message,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h4
        )
        if (onClickRetry != null) {
            Button(
                onClick = { onClickRetry() }
            ) {
                Text(text = "Retry")
            }
        }
    }
}

@Preview
@Composable
fun ErrorMessagePreview() {
    NasaExploreScreen {
        ErrorMessage(error = Error.Connectivity) {}
    }
}
