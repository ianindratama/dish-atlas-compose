package com.ianindratama.submission

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread
import com.ianindratama.submission.model.FakeFoodDataSource
import com.ianindratama.submission.ui.navigation.Screen
import com.ianindratama.submission.ui.theme.SubmissionTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FoodsAppTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController

    @Before
    fun setUp(){
        composeTestRule.setContent {
            SubmissionTheme {
                navController = TestNavHostController(LocalContext.current)
                navController.navigatorProvider.addNavigator(ComposeNavigator())
                FoodsApp(navController = navController)
            }
        }
    }

    @Test
    fun navHost_verifyStartDestination(){
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHost_verifyBottomNavigationWorking(){
        composeTestRule.onNodeWithContentDescriptionWithStringId(R.string.menu_home_description, useUnmergedTree = true).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)

        composeTestRule.onNodeWithContentDescriptionWithStringId(R.string.menu_favorite_description, useUnmergedTree = true).performClick()
        navController.assertCurrentRouteName(Screen.Favorite.route)

        composeTestRule.onNodeWithContentDescriptionWithStringId(R.string.menu_profile_description, useUnmergedTree = true).performClick()
        navController.assertCurrentRouteName(Screen.Profile.route)
    }

    @Test
    fun navHost_verifyBottomNavigationBackToHomeScreenUsingDeviceBackButton() {
        composeTestRule.onNodeWithTextWithStringId(R.string.menu_favorite).performClick()
        runOnUiThread {
            navController.navigateUp()
        }
        navController.assertCurrentRouteName(Screen.Home.route)

        composeTestRule.onNodeWithTextWithStringId(R.string.menu_favorite).performClick()
        composeTestRule.onNodeWithTextWithStringId(R.string.menu_profile).performClick()

        runOnUiThread {
            navController.navigateUp()
        }
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHost_clickItem_navigateBackUsingBackIcon(){
        composeTestRule.onNodeWithText(FakeFoodDataSource.dummyFoods[0].name).performClick()
        composeTestRule.onNodeWithContentDescriptionWithStringId(R.string.back).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHost_clickItem_verifyDisplayedDataOnDetailScreen(){
        composeTestRule.onNodeWithTagWithStringId(R.string.food_list).performScrollToIndex(9)
        composeTestRule.onNodeWithText(FakeFoodDataSource.dummyFoods[9].name).performClick()
        navController.assertCurrentRouteName(Screen.DetailFood.route)
        composeTestRule.onNodeWithText("Description: ${FakeFoodDataSource.dummyFoods[9].description}").assertIsDisplayed()
    }

    @Test
    fun navHost_verifyAddedItemOnFavoriteScreen(){
        composeTestRule.onNodeWithText(FakeFoodDataSource.dummyFoods[0].name).performClick()
        composeTestRule.onNodeWithContentDescriptionWithStringId(R.string.add_food_to_favorite).performClick()
        composeTestRule.onNodeWithContentDescriptionWithStringId(R.string.back).performClick()

        composeTestRule.onNodeWithContentDescriptionWithStringId(R.string.menu_favorite_description, useUnmergedTree = true).performClick()
        composeTestRule.onNodeWithText(FakeFoodDataSource.dummyFoods[0].name).assertIsDisplayed()
    }

    @Test
    fun navHost_verifyAddedItemOnFavoriteScreenRemoved(){
        composeTestRule.onNodeWithText(FakeFoodDataSource.dummyFoods[1].name).performClick()
        navController.assertCurrentRouteName(Screen.DetailFood.route)
        composeTestRule.onNodeWithContentDescriptionWithStringId(R.string.add_food_to_favorite).performClick()
        composeTestRule.onNodeWithContentDescriptionWithStringId(R.string.back).performClick()

        composeTestRule.onNodeWithContentDescriptionWithStringId(R.string.menu_favorite_description, useUnmergedTree = true).performClick()
        composeTestRule.onNodeWithText(FakeFoodDataSource.dummyFoods[1].name).performClick()
        composeTestRule.onNodeWithContentDescriptionWithStringId(R.string.remove_food_from_favorite).performClick()
        composeTestRule.onNodeWithContentDescriptionWithStringId(R.string.back).performClick()

        composeTestRule.onNodeWithText(FakeFoodDataSource.dummyFoods[1].name).assertIsNotDisplayed()
    }

}