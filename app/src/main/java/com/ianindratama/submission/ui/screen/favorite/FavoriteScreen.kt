package com.ianindratama.submission.ui.screen.favorite

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ianindratama.submission.R
import com.ianindratama.submission.ViewModelFactory
import com.ianindratama.submission.di.Injection
import com.ianindratama.submission.model.Food
import com.ianindratama.submission.ui.common.UiState
import com.ianindratama.submission.ui.components.FoodListItem
import com.ianindratama.submission.ui.components.NoDataToBeDisplayedContent

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getFavoriteFoods()
            }
            is UiState.Success -> {
                FavoriteContent(
                    favoriteFoods = uiState.data,
                    navigateToDetail = navigateToDetail,
                    modifier = modifier
                )
            }
            is UiState.Error -> {
                NoDataToBeDisplayedContent(
                    text = stringResource(
                        id = R.string.error_has_occured
                    )
                )
            }
        }
    }
}

@Composable
fun FavoriteContent(
    favoriteFoods: List<Food>,
    navigateToDetail: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    if (favoriteFoods.isNotEmpty()){
        val listState = rememberLazyListState()
        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(bottom = 40.dp),
            modifier = modifier
                .testTag(stringResource(id = R.string.favorite_food_list))
        ) {
            items(favoriteFoods, key = { it.id }) { food ->
                FoodListItem(
                    id = food.id,
                    name = food.name,
                    origin = food.origin,
                    photoUrl = food.foodPictureUrl,
                    navigateToDetail = navigateToDetail,
                    modifier = modifier
                        .fillMaxWidth()
                )
            }
        }
    } else {
        NoDataToBeDisplayedContent(
            text = stringResource(
                id = R.string.no_favorite_foods_available
            ),
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight()
        )
    }
}