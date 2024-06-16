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

    fun getFoodById(foodId: Long): Food?{
        return listOfFavoriteFood.firstOrNull {
            it.id == foodId
        }
    }

    fun searchFoods(query: String): List<Food> {
        return listOfFavoriteFood.filter {
            it.name.contains(query, ignoreCase = true)
        }
    }

    fun getFavoriteFoods(): Flow<List<Food>>{
        val favoriteFoods = listOfFavoriteFood.filter { it.isFavorite }
        return flowOf(favoriteFoods)
    }

    fun setIsFavoriteFood(foodId: Long, isFavoriteFood: Boolean){
        with(listOfFavoriteFood.first{ it.id == foodId }){
            this.isFavorite = !isFavoriteFood
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