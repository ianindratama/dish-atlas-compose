package com.ianindratama.submission.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ianindratama.submission.data.FoodRepository
import com.ianindratama.submission.model.Food
import com.ianindratama.submission.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val repository: FoodRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Food>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Food>>>
        get() = _uiState

    fun getFavoriteFoods() {
        viewModelScope.launch {
            repository.getFavoriteFoods()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }.collect { foods ->
                    _uiState.value = UiState.Success(foods)
                }
        }
    }
}