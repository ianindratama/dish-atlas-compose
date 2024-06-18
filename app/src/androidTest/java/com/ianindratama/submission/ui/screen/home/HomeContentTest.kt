package com.ianindratama.submission.ui.screen.home

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import com.ianindratama.submission.R
import com.ianindratama.submission.model.FakeFoodDataSource
import com.ianindratama.submission.onNodeWithContentDescriptionWithStringId
import com.ianindratama.submission.onNodeWithTagWithStringId
import com.ianindratama.submission.ui.theme.SubmissionTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeContentTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val fakeFoodList = FakeFoodDataSource.dummyFoods

    @Before
    fun setUp(){
        composeTestRule.setContent {
            SubmissionTheme {
                HomeContent(foods = fakeFoodList, navigateToDetail = {})
            }
        }
    }

    @Test
    fun checkIfFoodItems_isDisplayed(){
        composeTestRule.onNodeWithTagWithStringId(R.string.food_list).assertExists()

        composeTestRule.onNodeWithText(fakeFoodList[0].name).assertIsDisplayed()

        composeTestRule.onNodeWithTagWithStringId(R.string.food_list).performScrollToIndex(9)
        composeTestRule.onNodeWithText(fakeFoodList[9].name).assertIsDisplayed()
    }

    @Test
    fun checkIfScrollToTopButton_isWorking(){
        composeTestRule.onNodeWithTagWithStringId(R.string.food_list).performScrollToIndex(9)

        composeTestRule.onNodeWithText(fakeFoodList[0].name).assertIsNotDisplayed()
        composeTestRule.onNodeWithContentDescriptionWithStringId(R.string.scroll_to_top).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescriptionWithStringId(R.string.scroll_to_top).performClick()

        composeTestRule.onNodeWithText(fakeFoodList[0].name).assertIsDisplayed()
    }

    @Test
    fun checkIfItemOnList_isClickable(){
        composeTestRule.onNodeWithText(fakeFoodList[0].name).assertHasClickAction()
    }

}