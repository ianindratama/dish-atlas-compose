package com.ianindratama.submission.di

import com.ianindratama.submission.data.FoodRepository

object Injection {

    fun provideRepository(): FoodRepository {
        return FoodRepository.getInstance()
    }

}