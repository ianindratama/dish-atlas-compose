package com.ianindratama.submission.ui.screen.profile

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    ProfileContent()
}

@Composable
fun ProfileContent(modifier: Modifier = Modifier) {
    Scaffold {
        Text("Hi from Profile", modifier = Modifier.padding(it))
    }
}