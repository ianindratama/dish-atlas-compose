package com.ianindratama.submission.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ianindratama.submission.data.FoodRepository
import com.ianindratama.submission.model.Food
import com.ianindratama.submission.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: FoodRepository
): ViewModel() {

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    private val _uiState: MutableStateFlow<UiState<List<Food>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Food>>>
        get() = _uiState

    fun search(newQuery: String) {
        _query.value = newQuery
        _uiState.value = UiState.Success(
            repository.searchFoods(_query.value)
                .sortedBy { it.name }
        )
    }

    fun clearQuery(){
        _query.value = ""
    }

    fun getAllFoods() {
        viewModelScope.launch {
            repository.getAllFoods()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }.collect { foods ->
                    _uiState.value = UiState.Success(foods)
                }
        }
    }

}