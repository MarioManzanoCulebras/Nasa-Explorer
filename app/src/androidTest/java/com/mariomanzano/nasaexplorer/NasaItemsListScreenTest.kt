package com.mariomanzano.nasaexplorer

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import com.mariomanzano.domain.entities.PictureOfDayItem
import com.mariomanzano.nasaexplorer.ui.screens.common.NasaItemsListScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*

@ExperimentalFoundationApi
@ExperimentalMaterialApi
class NasaItemsListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val ctx = InstrumentationRegistry.getInstrumentation().targetContext

    private val items: List<PictureOfDayItem> = (1..100).map {
        PictureOfDayItem(
            id = it,
            date = Calendar.getInstance().apply { add(Calendar.DAY_OF_MONTH, it) },
            title = "Title $it",
            description = "Description $it",
            url = "",
            mediaType = "image",
            favorite = false,
            copyRight = "copyRight"
        )
    }

    @Before
    fun setUp() {
        composeTestRule.setContent {
            NasaItemsListScreen(
                items = items,
                onClick = {},
                onItemsMoreClicked = {},
                listState = rememberLazyListState()
            )
        }
    }

    @Test
    fun navigatesTo51(): Unit = with(composeTestRule) {
        onNode(hasScrollToIndexAction()).performScrollToIndex(25)
        onNodeWithText("Title 51").assertExists()
    }

    @Test
    fun navigatesTo51AndShowsBottomSheet(): Unit = with(composeTestRule) {
        onNode(hasScrollToIndexAction()).performScrollToIndex(25)
        onNode(
            hasParent(hasText("Title 51")) and
                    hasContentDescription(ctx.getString(R.string.more_options))
        ).performClick()

        onNode(
            hasAnySibling(hasText(ctx.getString(R.string.go_to_detail))) and
                    hasText("Title 51")
        ).assertExists()

        onRoot(useUnmergedTree = true).printToLog("semanticTree")
    }
}