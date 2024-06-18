package com.ianindratama.submission.ui.screen.favorite

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import com.ianindratama.submission.R
import com.ianindratama.submission.onNodeWithTagWithStringId
import com.ianindratama.submission.ui.screen.detail.FavoriteButton
import com.ianindratama.submission.ui.theme.SubmissionTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FavoriteFABTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private var isFavoriteDummy = false

    @Before
    fun setUp() {
        composeTestRule.setContent {
            SubmissionTheme {
                FavoriteButton(
                    id = 1,
                    isFavorite = isFavoriteDummy,
                    onSetFoodFavorite = { _, _ -> isFavoriteDummy = !isFavoriteDummy})
            }
        }
    }

    @Test
    fun checkIfFavoriteButton_isWorking() {
        composeTestRule.onNodeWithTagWithStringId(R.string.remove_food_from_favorite).assertIsNotDisplayed()

        if (isFavoriteDummy) assert(false)

        composeTestRule.onNodeWithTagWithStringId(R.string.fab_favorite_button).performClick()

        if (!isFavoriteDummy) assert(false)
    }

}