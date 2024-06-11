package com.ianindratama.submission.ui.screen.favorite

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun FavoriteScreen(modifier: Modifier = Modifier) {
    FavoriteContent()
}

@Composable
fun FavoriteContent(modifier: Modifier = Modifier) {
    Scaffold {
        Text(text = "Hi from Favorite", modifier = Modifier.padding(it))
    }
}