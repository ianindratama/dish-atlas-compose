package com.ianindratama.submission.ui.screen.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ianindratama.submission.R
import com.ianindratama.submission.ViewModelFactory
import com.ianindratama.submission.di.Injection
import com.ianindratama.submission.model.Food
import com.ianindratama.submission.ui.common.UiState
import com.ianindratama.submission.ui.components.FoodListItem
import com.ianindratama.submission.ui.components.NoDataToBeDisplayedContent
import com.ianindratama.submission.ui.theme.SubmissionTheme
import kotlinx.coroutines.launch

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
                Column(
                    modifier = modifier
                ) {
                    SearchBar(
                        query = query,
                        onQueryChange = viewModel::search,
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.primary)
                    )
                    HomeContent(
                        foods = uiState.data,
                        navigateToDetail = { foodId ->
                            viewModel.clearQuery()
                            navigateToDetail(foodId)
                        },
                        modifier = modifier
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
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val showUpButton: Boolean by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 0 }
    }
    Box(
        modifier = modifier
    ) {
        if (foods.isNotEmpty()) {
            LazyColumn(
                state = listState,
                contentPadding = PaddingValues(bottom = 40.dp),
                modifier = modifier
                    .testTag(stringResource(id = R.string.food_list))
            ){
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
            }
        } else {
            NoDataToBeDisplayedContent(
                text = stringResource(
                    id = R.string.no_foods_available
                ),
                modifier = modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            )
        }

        AnimatedVisibility(
            visible = showUpButton,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically(),
            modifier = Modifier
                .padding(bottom = 30.dp)
                .align(Alignment.BottomCenter)
        ) {
            ScrollToTopButton(
                onClick = {
                    scope.launch {
                        listState.animateScrollToItem(index = 0)
                    }
                }
            )
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
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .heightIn(min = 48.dp)
    ){

    }
}

@Composable
fun ScrollToTopButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FilledIconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Filled.KeyboardArrowUp,
            contentDescription = stringResource(id = R.string.scroll_to_top)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    SubmissionTheme {
        HomeScreen {

        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchBarPreview() {
    SubmissionTheme {
        SearchBar(query = "", onQueryChange = {})
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

