package com.ianindratama.submission.data

import com.ianindratama.submission.model.FakeFoodDataSource
import com.ianindratama.submission.model.Food
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FoodRepository {

    private val listOfFavoriteFood = mutableListOf<Food>()

    init {
        if (listOfFavoriteFood.isEmpty()) {
            FakeFoodDataSource.dummyFoods.forEach{
                listOfFavoriteFood.add(it)
            }
        }
    }

    fun getAllFoods(): Flow<List<Food>> {
        return flowOf(listOfFavoriteFood)
    }

    fun getFoodById(foodId: Long): Food{
        return listOfFavoriteFood.first {
            it.id == foodId
        }
    }

    companion object {

        @Volatile
        private var instance: FoodRepository?= null

        fun getInstance(): FoodRepository =
            instance ?: synchronized(this) {
                FoodRepository().apply {
                    instance = this
                }
            }

    }

}