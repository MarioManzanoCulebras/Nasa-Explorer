package com.mariomanzano.nasaexplorer.ui.screens.common

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.mariomanzano.domain.Error
import com.mariomanzano.domain.entities.NasaItem
import com.mariomanzano.domain.entities.PictureOfDayItem
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun <T : NasaItem> NasaItemsListScreen(
    loading: Boolean = false,
    items: List<T>?,
    onClick: (T) -> Unit,
    onRefreshComplete: (() -> Unit)? = null,
    onSimpleRefresh: (() -> Unit)? = null,
    error: Error? = null,
    listState: LazyGridState,
    onItemsMoreClicked: () -> Unit,
) {
    if (error != null && !loading && (items == null || items.isEmpty())) {
        ErrorMessage(error = error, onRefreshComplete)
    } else {
        var bottomSheetItem by remember { mutableStateOf<T?>(null) }
        val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
        val scope = rememberCoroutineScope()

        BackHandler(sheetState.isVisible) {
            scope.launch { sheetState.hide() }
        }

        ModalBottomSheetLayout(
            sheetContent = {
                NasaItemBottomPreview(
                    item = bottomSheetItem,
                    onGoToDetail = {
                        scope.launch {
                            sheetState.hide()
                            onClick(it)
                        }
                    }
                )
            },
            sheetState = sheetState
        ) {
            NasaItemsList(
                loading = loading,
                items = items,
                onItemClick = onClick,
                onItemMore = {
                    onItemsMoreClicked.invoke()
                    scope.launch {
                        bottomSheetItem = it
                        sheetState.animateTo(ModalBottomSheetValue.Expanded)
                    }
                },
                onRefresh = onSimpleRefresh,
                listState = listState
            )
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun PODItemsListScreen(
    loading: Boolean = false,
    items: List<PictureOfDayItem>?,
    onClick: (PictureOfDayItem) -> Unit,
    onRefreshComplete: () -> Unit,
    onSimpleRefresh: () -> Unit,
    error: Error?,
    listState: LazyListState,
    onItemsMoreClicked: () -> Unit
) {
    if (error != null && !loading && items?.isEmpty() == true) {
        ErrorMessage(error = error, onRefreshComplete)
    } else {
        var bottomSheetItem by remember { mutableStateOf<PictureOfDayItem?>(null) }
        val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
        val scope = rememberCoroutineScope()

        BackHandler(sheetState.isVisible) {
            scope.launch { sheetState.hide() }
        }

        ModalBottomSheetLayout(
            sheetContent = {
                NasaItemBottomPreview(
                    item = bottomSheetItem,
                    onGoToDetail = {
                        scope.launch {
                            sheetState.hide()
                            onClick(it)
                        }
                    }
                )
            },
            sheetState = sheetState
        ) {
            PODItemsList(
                loading = loading,
                items = items,
                onItemClick = onClick,
                onItemMore = {
                    onItemsMoreClicked.invoke()
                    scope.launch {
                        bottomSheetItem = it
                        sheetState.show()
                    }
                },
                onSimpleRefresh = onSimpleRefresh,
                listState = listState
            )
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun <T : NasaItem> NasaItemsList(
    loading: Boolean,
    items: List<T>?,
    onItemClick: (T) -> Unit,
    onItemMore: (T) -> Unit,
    onRefresh: (() -> Unit)?,
    listState: LazyGridState,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        if (loading) {
            CircularProgressIndicator()
        }

        items?.let { list ->
            if (list.isNotEmpty()) {
                SwipeRefresh(
                    state = rememberSwipeRefreshState(loading),
                    onRefresh = { onRefresh?.invoke() },
                    swipeEnabled = onRefresh != null
                ) {
                    LazyVerticalGrid(
                        state = listState,
                        columns = GridCells.Adaptive(180.dp),
                        contentPadding = PaddingValues(4.dp)
                    ) {

                        items(list) {
                            NasaListItem(
                                nasaItem = it,
                                onItemMore = onItemMore,
                                showFooterInside = false,
                                modifier = Modifier.clickable { onItemClick(it) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun PODItemsList(
    loading: Boolean,
    items: List<PictureOfDayItem>?,
    onItemClick: (PictureOfDayItem) -> Unit,
    onItemMore: (PictureOfDayItem) -> Unit,
    onSimpleRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    listState: LazyListState
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        if (loading) {
            CircularProgressIndicator()
        }

        items?.let { list ->
            if (list.isNotEmpty()) {
                SwipeRefresh(
                    state = rememberSwipeRefreshState(loading),
                    onRefresh = { onSimpleRefresh() },
                ) {
                    LazyColumn(
                        state = listState,
                        contentPadding = PaddingValues(4.dp)
                    ) {
                        items(list) {
                            NasaListItem(
                                nasaItem = it,
                                onItemMore = onItemMore,
                                showFooterInside = true,
                                modifier = Modifier.clickable { onItemClick(it) }
                            )
                        }
                    }
                }
            }
        }
    }
}