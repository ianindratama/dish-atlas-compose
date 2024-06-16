package com.ianindratama.submission.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ianindratama.submission.data.FoodRepository
import com.ianindratama.submission.model.Food
import com.ianindratama.submission.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailFoodViewModel(
    private val repository: FoodRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<Food>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Food>>
        get() = _uiState

    private val _isFavorite: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean>
        get() = _isFavorite

    fun getFoodById(foodId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            val food: Food? = repository.getFoodById(foodId)
            if (food != null) {
                _isFavorite.value = food.isFavorite
                _uiState.value = UiState.Success(food)
            } else {
                _uiState.value = UiState.Error("An error has occurred")
            }
        }
    }

    fun setIsFavoriteFood(foodId: Long, isFavoriteFood: Boolean) {
        viewModelScope.launch {
            repository.setIsFavoriteFood(foodId, isFavoriteFood)
            getFoodById(foodId)
        }
    }

}