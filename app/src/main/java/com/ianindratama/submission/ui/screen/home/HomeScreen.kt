package com.ianindratama.submission.ui.screen.home

import android.widget.Toast
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
    navigateToDetail: (Long) -> Unit
) {

    val query by viewModel.query

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState -> 
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllFoods()
            }
            is UiState.Success -> {
                HomeContent(
                    foods = uiState.data,
                    searchFoodQuery = query,
                    searchFoodFunction = viewModel::search,
                    navigateToDetail = navigateToDetail,
                    modifier = modifier
                )
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
    searchFoodQuery: String,
    searchFoodFunction: (String) -> Unit,
    navigateToDetail: (Long) -> Unit,
    modifier: Modifier = Modifier
) {

    Box(modifier = modifier){
        val scope = rememberCoroutineScope()
        val listState = rememberLazyListState()
        val showUpButton: Boolean by remember {
            derivedStateOf { listState.firstVisibleItemIndex > 0 }
        }

        LazyColumn(
            state = listState
        ) {
            item {
                SearchBar(
                    query = searchFoodQuery,
                    onQueryChange = searchFoodFunction,
                    modifier = Modifier.background(MaterialTheme.colorScheme.primary)
                )
            }
            if (foods.isNotEmpty()){
                items(foods, key = { it.id }) { food ->
                    FoodListItem(
                        id = food.id,
                        name = food.name,
                        origin = food.origin,
                        photoUrl = food.foodPictureUrl,
                        navigateToDetail = navigateToDetail,
                        modifier = modifier
                            .fillMaxWidth()
                            .animateItemPlacement(tween(durationMillis = 100))
                    )
                }
            } else {
                item {
                    NoDataToBeDisplayedContent(
                        text = stringResource(id = R.string.no_foods_available)
                    )
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    androidx.compose.material3.SearchBar(
        query = query,
        onQueryChange = onQueryChange,
        onSearch = {},
        active = false,
        onActiveChange = {},
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(id = R.string.search)
            )
        },
        placeholder = {
            Text(text = stringResource(id = R.string.search_food))
        },
        shape = MaterialTheme.shapes.large,
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .heightIn(min = 48.dp)
    ) {

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
    id: Long,
    name: String,
    origin: String,
    photoUrl: String,
    navigateToDetail: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable {
            navigateToDetail(id)
        }
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
                text = "Origin: $origin",
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
            id = 1,
            name = "Nasi Goreng",
            origin = "Indonesia",
            photoUrl = "",
            navigateToDetail = {}
        )
    }
}