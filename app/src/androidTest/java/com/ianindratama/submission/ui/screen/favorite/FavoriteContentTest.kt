package com.ianindratama.submission.ui.screen.favorite

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.ianindratama.submission.R
import com.ianindratama.submission.model.FakeFoodDataSource
import com.ianindratama.submission.model.Food
import com.ianindratama.submission.onNodeWithTagWithStringId
import com.ianindratama.submission.onNodeWithTextWithStringId
import com.ianindratama.submission.ui.theme.SubmissionTheme
import org.junit.Rule
import org.junit.Test

class FavoriteContentTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun checkIfNoDataToBeDisplayedContent_isDisplayed_whenThereIsNoFavoriteFood(){
        val fakeFavoriteFoodList = emptyList<Food>()

        composeTestRule.setContent {
            SubmissionTheme {
                FavoriteContent(favoriteFoods = fakeFavoriteFoodList, navigateToDetail = {})
            }
        }

        composeTestRule.onNodeWithTagWithStringId(R.string.favorite_food_list).assertIsNotDisplayed()
        composeTestRule.onNodeWithTextWithStringId(R.string.no_favorite_foods_available).assertIsDisplayed()
    }

    @Test
    fun checkIfFavoriteFoodList_isDisplayed_whenThereAreFavoriteFoods(){
        val fakeFoodList = FakeFoodDataSource.dummyFoods

        val fakeFavoriteFoodList = fakeFoodList.filter { food ->
            (food.id == 1L || food.id == 10L)
        }.map {
            it.copy(isFavorite = true)
        }

        composeTestRule.setContent {
            SubmissionTheme {
                FavoriteContent(favoriteFoods = fakeFavoriteFoodList, navigateToDetail = {})
            }
        }

        composeTestRule.onNodeWithTagWithStringId(R.string.favorite_food_list).assertIsDisplayed()
        composeTestRule.onNodeWithText(fakeFoodList[0].name).assertIsDisplayed()
        composeTestRule.onNodeWithText(fakeFoodList[9].name).assertIsDisplayed()
    }

    @Test
    fun checkIfFavoriteFoodItem_hasClickAction(){
        val fakeFoodList = FakeFoodDataSource.dummyFoods

        val fakeFavoriteFoodList = fakeFoodList.filter { food ->
            food.id == 1L
        }.map {
            it.copy(isFavorite = true)
        }

        composeTestRule.setContent {
            SubmissionTheme {
                FavoriteContent(favoriteFoods = fakeFavoriteFoodList, navigateToDetail = {})
            }
        }

        composeTestRule.onNodeWithTagWithStringId(R.string.favorite_food_list).assertIsDisplayed()
        composeTestRule.onNodeWithText(fakeFoodList[0].name).assertHasClickAction()
    }

}