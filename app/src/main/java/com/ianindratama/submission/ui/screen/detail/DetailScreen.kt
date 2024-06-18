package com.ianindratama.submission.ui.screen.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.ianindratama.submission.R
import com.ianindratama.submission.ViewModelFactory
import com.ianindratama.submission.di.Injection
import com.ianindratama.submission.ui.common.UiState
import com.ianindratama.submission.ui.components.NoDataToBeDisplayedContent

@Composable
fun DetailScreen(
    foodId: Long,
    modifier: Modifier = Modifier,
    viewModel: DetailFoodViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateBack: () -> Unit
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getFoodById(foodId)
            }
            is UiState.Success -> {
                val data = uiState.data
                val isFavorite by viewModel.isFavorite.collectAsState()

                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    DetailContent(
                        name = data.name,
                        pictureUrl = data.foodPictureUrl,
                        origin = data.origin,
                        timeToMake = data.timeToMake,
                        description = data.description,
                        onBackClick = navigateBack,
                        modifier = modifier
                    )
                    FavoriteButton(
                        id = foodId,
                        isFavorite = isFavorite,
                        onSetFoodFavorite = viewModel::setIsFavoriteFood,
                        modifier = modifier
                            .padding(16.dp)
                            .align(Alignment.BottomEnd)
                    )
                }
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
fun DetailContent(
    name: String,
    pictureUrl: String,
    origin: String,
    timeToMake: String,
    description: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        Box {
            AsyncImage(
                model = pictureUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .height(400.dp)
                    .fillMaxWidth()
                    .testTag(pictureUrl)
            )
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = stringResource(id = R.string.back),
                modifier = modifier
                    .padding(16.dp)
                    .size(32.dp)
                    .background(
                        color = Color.White,
                        shape = CircleShape,
                    )
                    .clickable { onBackClick() }
            )
        }
        Text(
            text = name,
            style = MaterialTheme.typography.headlineSmall.copy(
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .padding(8.dp)
        )
        FoodInformationDetail(
            icon = Icons.Default.LocationOn,
            itemContentDescription = stringResource(id = R.string.origin),
            information = stringResource(R.string.origin_value, origin)
        )
        FoodInformationDetail(
            icon = Icons.Default.DateRange,
            itemContentDescription = stringResource(id = R.string.timeToMake),
            information = stringResource(R.string.timeToMake_value, timeToMake)
        )
        FoodInformationDetail(
            icon = Icons.Default.Info,
            itemContentDescription = stringResource(id = R.string.description),
            information = stringResource(R.string.description_value, description)
        )
    }
}

@Composable
fun FavoriteButton(
    id: Long,
    isFavorite: Boolean,
    onSetFoodFavorite: (Long, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    FloatingActionButton(
        onClick = { onSetFoodFavorite(id, isFavorite) },
        modifier = modifier
            .testTag(stringResource(id = R.string.fab_favorite_button))
    ) {
        if (isFavorite) {
            Icon(imageVector = Icons.Default.Favorite, contentDescription = stringResource(id = R.string.remove_food_from_favorite))
        } else {
            Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = stringResource(id = R.string.add_food_to_favorite))
        }
    }
}

@Composable
fun FoodInformationDetail(
    icon: ImageVector,
    itemContentDescription: String,
    information: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(8.dp)
            .testTag(itemContentDescription)
    ) {
        Icon(imageVector = icon, contentDescription = itemContentDescription)
        Text(
            text = information,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 16.sp
            ),
            modifier = Modifier
                .padding(horizontal = 4.dp)
        )
    }
}