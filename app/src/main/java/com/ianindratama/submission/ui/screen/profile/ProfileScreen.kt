package com.ianindratama.submission.ui.screen.profile

import android.content.Context
import android.content.Intent
import android.net.Uri
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ianindratama.submission.R

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    ProfileContent(
        image = R.drawable.creator_photo,
        name = stringResource(id = R.string.creator_name),
        email = stringResource(id = R.string.creator_email),
        url = stringResource(id = R.string.creator_url),
        onClick = { toCreatorPage(context, context.getString(R.string.creator_url)) },
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

private fun toCreatorPage(context: Context, url: String) {

    val webPage = Uri.parse(url)
    val intent = Intent(Intent.ACTION_VIEW, webPage)

    context.startActivity(
        Intent.createChooser(
            intent,
            context.getString(R.string.pick_a_browser)
        )
    )
}