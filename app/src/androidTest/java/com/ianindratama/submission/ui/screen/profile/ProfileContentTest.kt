package com.ianindratama.submission.ui.screen.profile

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.ianindratama.submission.R
import com.ianindratama.submission.onNodeWithTextWithStringId
import com.ianindratama.submission.ui.theme.SubmissionTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ProfileContentTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val dummyImage = R.drawable.creator_photo
    private val dummyName = R.string.creator_name
    private val dummyEmail = R.string.creator_email
    private val dummyUrl = R.string.creator_url
    private val dummyOnClick: (String) -> Unit = {}

    @Before
    fun setUp(){
        composeTestRule.setContent {
            SubmissionTheme {
                ProfileContent(
                    image = dummyImage,
                    name = composeTestRule.activity.getString(dummyName),
                    email = composeTestRule.activity.getString(dummyEmail),
                    url = composeTestRule.activity.getString(dummyUrl),
                    onClick = dummyOnClick
                )
            }
        }
    }

    @Test
    fun checkIfCreatorData_isDisplayedCorrectly(){
        composeTestRule.onNodeWithTag(dummyImage.toString()).assertIsDisplayed()
        composeTestRule.onNodeWithTextWithStringId(dummyName).assertIsDisplayed()
        composeTestRule.onNodeWithTextWithStringId(dummyEmail).assertIsDisplayed()
    }

    @Test
    fun checkIfGetToKnowMoreButton_hasClickAction(){
        composeTestRule.onNodeWithTextWithStringId(R.string.get_to_know_me_more).assertHasClickAction()
    }

}