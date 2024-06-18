package com.ianindratama.submission

import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.rules.ActivityScenarioRule

fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.onNodeWithTextWithStringId(
    @StringRes id: Int
): SemanticsNodeInteraction = onNodeWithText(activity.getString(id))

fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.onNodeWithContentDescriptionWithStringId(
    @StringRes id: Int,
    useUnmergedTree: Boolean = false
): SemanticsNodeInteraction = onNodeWithContentDescription(activity.getString(id), useUnmergedTree = useUnmergedTree)

fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.onNodeWithTagWithStringId(
    @StringRes id: Int,
    useUnmergedTree: Boolean = false
): SemanticsNodeInteraction = onNodeWithTag(activity.getString(id), useUnmergedTree = useUnmergedTree)

