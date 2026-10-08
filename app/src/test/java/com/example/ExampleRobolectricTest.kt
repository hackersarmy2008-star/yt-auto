package com.example

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun shortsPilotDashboardAndNavigationWorkflow_rendersAndSwitchesWorkstations() {
        composeTestRule.waitForIdle()
        // Verify primary Autopilot trigger and Auto Mode switch exist on Dashboard
        composeTestRule.onNodeWithTag("run_autopilot_cycle_button").assertExists()
        composeTestRule.onNodeWithTag("auto_mode_switch").assertExists()

        // Navigate to "Why Failed?" Diagnostic Workstation via Bottom Bar
        composeTestRule.onNodeWithTag("bottom_nav_failed_shorts").performClick()
        composeTestRule.waitForIdle()

        // Navigate to 9:16 Video Generator Studio
        composeTestRule.onNodeWithTag("bottom_nav_video_generator").performClick()
        composeTestRule.waitForIdle()

        // Navigate to AI Strategy & Content Learning System
        composeTestRule.onNodeWithTag("bottom_nav_ai_strategy").performClick()
        composeTestRule.waitForIdle()
    }
}
