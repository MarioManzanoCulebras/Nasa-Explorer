package com.mariomanzano.nasaexplorer.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.espresso.IdlingRegistry
import androidx.test.platform.app.InstrumentationRegistry
import com.mariomanzano.nasaexplorer.R
import com.mariomanzano.nasaexplorer.data.server.MockWebServerRule
import com.mariomanzano.nasaexplorer.data.server.fromJson
import com.mariomanzano.nasaexplorer.ui.screens.dailypicture.DailyPictureScreen
import com.mariomanzano.nasaexplorer.ui.screens.dailypicture.DailyPictureViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@HiltAndroidTest
class NasaItemsListScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val mockWebServerRule = MockWebServerRule()

    @get:Rule(order = 2)
    val composeTestRule = createComposeRule()

    private val ctx = InstrumentationRegistry.getInstrumentation().targetContext

    @Inject
    lateinit var okHttpClient: OkHttpClient

    @Inject
    lateinit var dailyPictureViewModel: DailyPictureViewModel

    @Before
    fun setUp() {
        mockWebServerRule.server.enqueue(
            MockResponse().fromJson("astronomy_picture_of_day_6_days_request.json")
        )

        hiltRule.inject()

        val resource = OkHttp3IdlingResource.create("OkHttp", okHttpClient)
        IdlingRegistry.getInstance().register(resource)

        composeTestRule.setContent {

            DailyPictureScreen(
                listState = rememberLazyListState(),
                onClick = {},
                onItemsMoreClicked = {},
                viewModel = dailyPictureViewModel
            )
        }
    }

    @Test
    fun navigatesTo4(): Unit = with(composeTestRule) {
        onRoot().printToLog("semanticTree")
        onNode(hasScrollToIndexAction()).performScrollToIndex(4)
        Thread.sleep(5000)
        onNodeWithText("Orion over Green Bank").assertExists()
    }

    @Test
    fun navigatesTo6AndShowsBottomSheet(): Unit = with(composeTestRule) {
        onRoot().printToLog("semanticTree")
        onNode(hasScrollToIndexAction()).performScrollToIndex(6)
        Thread.sleep(5000)
        onNode(
            hasParent(hasText("Aurora Over White Dome Geyser")) and
                    hasContentDescription(ctx.getString(R.string.more_options))
        ).performClick()
        Thread.sleep(5000)
        onNode(
            hasAnySibling(hasText(ctx.getString(R.string.go_to_detail))) and
                    hasText("Aurora Over White Dome Geyser", true)
        ).assertExists()
    }
}