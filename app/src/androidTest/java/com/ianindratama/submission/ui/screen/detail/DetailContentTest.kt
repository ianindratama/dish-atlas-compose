package com.ianindratama.submission.ui.screen.detail

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.ianindratama.submission.R
import com.ianindratama.submission.model.FakeFoodDataSource
import com.ianindratama.submission.onNodeWithContentDescriptionWithStringId
import com.ianindratama.submission.onNodeWithTagWithStringId
import com.ianindratama.submission.ui.theme.SubmissionTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailContentTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private var dummyFood = FakeFoodDataSource.dummyFoods[0]

    @Before
    fun setUp(){
        composeTestRule.setContent {
            SubmissionTheme {
                DetailContent(
                    name = dummyFood.name,
                    pictureUrl = dummyFood.foodPictureUrl,
                    origin = dummyFood.origin,
                    timeToMake = dummyFood.timeToMake,
                    description = dummyFood.description,
                    onBackClick = {}
                )
            }
        }
    }

    @Test
    fun checkIfFoodData_isDisplayedCorrectly(){
        composeTestRule.onNodeWithText(dummyFood.name).assertIsDisplayed()
        composeTestRule.onNodeWithTag(dummyFood.foodPictureUrl).assertIsDisplayed()

        composeTestRule.onNodeWithTagWithStringId(R.string.origin)
            .onChildren().assertAny(hasText("Origin: ${dummyFood.origin}"))

        composeTestRule.onNodeWithTagWithStringId(R.string.timeToMake)
            .onChildren().assertAny(hasText("Time to make: ${dummyFood.timeToMake}"))

        composeTestRule.onNodeWithTagWithStringId(R.string.description)
            .onChildren().assertAny(hasText("Description: ${dummyFood.description}"))
    }

    @Test
    fun checkIfBackIcon_hasClickAction(){
        composeTestRule.onNodeWithContentDescriptionWithStringId(R.string.back).assertHasClickAction()
    }

}