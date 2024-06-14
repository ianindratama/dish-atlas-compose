package com.ianindratama.submission

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ianindratama.submission.data.FoodRepository
import com.ianindratama.submission.ui.screen.detail.DetailFoodViewModel
import com.ianindratama.submission.ui.screen.home.HomeViewModel

class ViewModelFactory(private val repository: FoodRepository): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailFoodViewModel::class.java)) {
            return DetailFoodViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

}