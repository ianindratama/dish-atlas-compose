package com.ianindratama.submission.ui.screen.home

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.ianindratama.submission.R
import com.ianindratama.submission.ViewModelFactory
import com.ianindratama.submission.data.FoodRepository
import com.ianindratama.submission.di.Injection
import com.ianindratama.submission.model.Food
import com.ianindratama.submission.ui.common.UiState
import com.ianindratama.submission.ui.theme.SubmissionTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit = {}
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState -> 
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllFoods()
            }
            is UiState.Success -> {
                if (uiState.data.isNotEmpty()){
                    HomeContent(
                        foods = uiState.data,
                        navigateToDetail = navigateToDetail,
                        modifier = modifier
                    )
                } else {
                    NoDataToBeDisplayedContent(
                        text = stringResource(id = R.string.no_foods_available)
                    )
                }
            }
            is UiState.Error -> {
                NoDataToBeDisplayedContent(
                    text = stringResource(id = R.string.error_has_occured)
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeContent(
    foods: List<Food>,
    navigateToDetail: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn {
        items(foods, key = { it.id }) { food ->
            FoodListItem(
                name = food.name,
                origin = food.origin,
                photoUrl = food.foodPictureUrl,
                modifier = modifier
                    .fillMaxWidth()
                    .animateItemPlacement(tween(durationMillis = 100))
            )
        }
    }
}

@Composable
fun NoDataToBeDisplayedContent(
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = modifier
    ){
        Text(text = text)
    }
}

@Composable
fun FoodListItem(
    name: String,
    origin: String,
    photoUrl: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable {  }
    ) {
        AsyncImage(
            model = photoUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(8.dp)
                .size(120.dp)
                .clip(CircleShape)
        )
        Column {
            Text(
                text = name,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
            )
            Text(
                text = "From: $origin",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FoodListItemPreview() {
    SubmissionTheme {
        FoodListItem(
            name = "Nasi Goreng",
            origin = "Indonesia",
            photoUrl = ""
        )
    }
}