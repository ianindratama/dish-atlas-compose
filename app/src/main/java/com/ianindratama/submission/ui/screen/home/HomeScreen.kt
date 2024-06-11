package com.ianindratama.submission.ui.screen.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    HomeContent()
}

@Composable
fun HomeContent(modifier: Modifier = Modifier) {
    Scaffold {
        Text(text = "Hi from Home", modifier = Modifier.padding(it))
    }
}