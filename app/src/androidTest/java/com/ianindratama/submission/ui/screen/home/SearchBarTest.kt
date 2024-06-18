package com.ianindratama.submission.ui.screen.home

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.performTextInput
import com.ianindratama.submission.R
import com.ianindratama.submission.model.FakeFoodDataSource
import com.ianindratama.submission.onNodeWithContentDescriptionWithStringId
import com.ianindratama.submission.ui.theme.SubmissionTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchBarTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val fakeFoodList = FakeFoodDataSource.dummyFoods
    private var searchFoodList = fakeFoodList

    private fun searchFoods(query: String){
        searchFoodList = fakeFoodList.filter {
            it.name.contains(query, ignoreCase = true)
        }
    }

    @Before
    fun setUp() {
        composeTestRule.setContent {
            SubmissionTheme {
                SearchBar(query = "", onQueryChange = ::searchFoods)
            }
        }
    }

    @Test
    fun checkListOfFoods_whenQueryIsEmpty(){
        composeTestRule.onNodeWithContentDescriptionWithStringId(R.string.search_food).onChildAt(0).performTextInput("")
        assert(searchFoodList.size == (fakeFoodList.filter { it.name.contains("", ignoreCase = true) }.size) )
    }

    @Test
    fun checkListOfFoods_whenQueryIsFilledThenCleared(){
        composeTestRule.onNodeWithContentDescriptionWithStringId(R.string.search_food).onChildAt(0).performTextInput("C")
        assert(searchFoodList.size == (fakeFoodList.filter { it.name.contains("C", ignoreCase = true) }.size) )

        composeTestRule.onNodeWithContentDescriptionWithStringId(R.string.search_food).onChildAt(0).performTextInput("Cr")
        assert(searchFoodList.size == (fakeFoodList.filter { it.name.contains("Cr", ignoreCase = true) }.size) )

        composeTestRule.onNodeWithContentDescriptionWithStringId(R.string.search_food).onChildAt(0).performTextInput("")
        assert(searchFoodList.size == (fakeFoodList.filter { it.name.contains("", ignoreCase = true) }.size) )
    }

}