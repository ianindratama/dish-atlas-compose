package com.ianindratama.submission.ui.navigation

sealed class Screen(val route: String) {

    object Home: Screen("home")
    object Favorite: Screen("favorite")
    object Profile: Screen("profile")
    object DetailFood: Screen("detail/{foodId}"){
        fun createRoute(foodId: Long) = "detail/$foodId"
    }

}