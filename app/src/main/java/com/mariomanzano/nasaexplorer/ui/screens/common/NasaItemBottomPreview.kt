package com.mariomanzano.nasaexplorer.ui.screens.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.mariomanzano.domain.entities.EarthItem
import com.mariomanzano.domain.entities.NasaItem
import com.mariomanzano.nasaexplorer.R

@Composable
fun <T : NasaItem> NasaItemBottomPreview(item: T?, onGoToDetail: (T) -> Unit) {
    if (item != null) {
        Row(
            modifier = Modifier.padding(20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (item is EarthItem) Text(
                    text = DateFormatter.OnlyTime.formatter.format(item.date.time),
                    style = MaterialTheme.typography.h4,
                )
                Text(text = DateFormatter.Simple.formatter.format(item.date.time))
                Text(text = item.title ?: "", style = MaterialTheme.typography.h6)
                Text(text = item.description ?: "", maxLines = 10, overflow = TextOverflow.Ellipsis)
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