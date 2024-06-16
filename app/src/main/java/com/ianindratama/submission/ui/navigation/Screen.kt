package com.ianindratama.submission.ui.navigation

sealed class Screen(val route: String) {

    data object Home: Screen("home")
    data object Favorite: Screen("favorite")
    data object Profile: Screen("profile")
    data object DetailFood: Screen("detail/{foodId}"){
        fun createRoute(foodId: Long) = "detail/$foodId"
    }

}