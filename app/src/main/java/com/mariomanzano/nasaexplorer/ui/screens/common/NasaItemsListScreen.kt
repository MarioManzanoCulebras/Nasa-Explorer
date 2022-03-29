package com.mariomanzano.nasaexplorer.ui.screens.common

import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
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
    onRefresh: (() -> Unit)? = null
) {
    var bottomSheetItem by remember { mutableStateOf<T?>(null) }
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    BackPressedHandler(sheetState.isVisible) {
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
                scope.launch {
                    bottomSheetItem = it
                    sheetState.show()
                }
            },
            onRefresh = onRefresh
        )
    }
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun PODItemsListScreen(
    loading: Boolean = false,
    items: List<PictureOfDayItem>?,
    onClick: (PictureOfDayItem) -> Unit,
    onRefresh: () -> Unit
) {
    var bottomSheetItem by remember { mutableStateOf<PictureOfDayItem?>(null) }
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    BackPressedHandler(sheetState.isVisible) {
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
                scope.launch {
                    bottomSheetItem = it
                    sheetState.show()
                }
            },
            onRefresh = onRefresh
        )
    }
}

@Composable
fun BackPressedHandler(enabled: Boolean, onBack: () -> Unit) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val backDispatcher =
        requireNotNull(LocalOnBackPressedDispatcherOwner.current).onBackPressedDispatcher

    val backCallback = remember {
        object : OnBackPressedCallback(enabled) {
            override fun handleOnBackPressed() {
                onBack()
            }
        }
    }

    SideEffect {
        backCallback.isEnabled = enabled
    }

    DisposableEffect(lifecycleOwner, backDispatcher) {
        backDispatcher.addCallback(lifecycleOwner, backCallback)

        onDispose { backCallback.remove() }
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
                        cells = GridCells.Adaptive(180.dp),
                        contentPadding = PaddingValues(4.dp)
                    ) {

                        items(list) {
                            NasaListItem(
                                nasaItem = it,
                                onItemMore = onItemMore,
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
    onRefresh: () -> Unit,
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
                    onRefresh = { onRefresh() },
                ) {
                    LazyColumn(
                        contentPadding = PaddingValues(4.dp)
                    ) {
                        items(list) {
                            NasaListItem(
                                nasaItem = it,
                                onItemMore = onItemMore,
                                modifier = Modifier.clickable { onItemClick(it) }
                            )
                        }
                    }
                }
            }
        }
    }
}