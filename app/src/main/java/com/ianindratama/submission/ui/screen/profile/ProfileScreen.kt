package com.ianindratama.submission.ui.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ianindratama.submission.R

@Composable
fun ProfileScreen(
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    ProfileContent(
        image = R.drawable.creator_photo,
        name = stringResource(id = R.string.creator_name),
        email = stringResource(id = R.string.creator_email),
        url = stringResource(id = R.string.creator_url),
        onClick = onClick,
        modifier = modifier
    )

}

@Composable
fun ProfileContent(
    image: Int,
    name: String,
    email: String,
    url: String,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .padding(16.dp)
                .size(240.dp)
                .clip(CircleShape)
        )
        Text(
            text = name,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = modifier
                .padding(16.dp)
        )
        Text(
            text = email,
            modifier = modifier
                .padding(16.dp)
        )
        Button(onClick = { onClick(url) }) {
            Text(
                text = stringResource(id = R.string.get_to_know_me_more)
            )
        }
    }
}