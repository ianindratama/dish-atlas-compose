package com.ianindratama.submission

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ianindratama.submission.ui.navigation.NavigationItem
import com.ianindratama.submission.ui.navigation.Screen
import com.ianindratama.submission.ui.screen.detail.DetailScreen
import com.ianindratama.submission.ui.screen.favorite.FavoriteScreen
import com.ianindratama.submission.ui.screen.home.HomeScreen
import com.ianindratama.submission.ui.screen.profile.ProfileScreen
import com.ianindratama.submission.ui.theme.SubmissionTheme

@Composable
fun FoodsApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val navigationItems = listOf(
        NavigationItem(
            title = stringResource(id = R.string.menu_home),
            icon = Icons.Default.Home,
            screen = Screen.Home,
            screenDescription = stringResource(id = R.string.menu_home_description)
        ),
        NavigationItem(
            title = stringResource(id = R.string.menu_favorite),
            icon = Icons.Default.Favorite,
            screen = Screen.Favorite,
            screenDescription = stringResource(id = R.string.menu_favorite_description)
        ),
        NavigationItem(
            title = stringResource(id = R.string.menu_profile),
            icon = Icons.Default.AccountCircle,
            screen = Screen.Profile,
            screenDescription = stringResource(id = R.string.menu_profile_description)
        )
    )
    
    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.DetailFood.route) {
                BottomBar(navigationItems = navigationItems, navController = navController)
            }
        },
        modifier = modifier
    ) { innerPadding ->
        
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToDetail = { foodId ->
                        navController.navigate(Screen.DetailFood.createRoute(foodId))
                    }
                )
            }
            composable(Screen.Favorite.route) {
                FavoriteScreen(modifier = modifier.background(Color.Cyan))
            }
            composable(Screen.Profile.route) {
                val context = LocalContext.current
                ProfileScreen(
                    onClick = {
                        toCreatorPage(context, it)
                    }
                )
            }
            composable(
                route = Screen.DetailFood.route,
                arguments = listOf(
                    navArgument("foodId") {
                        type = NavType.LongType
                    }
                )
            ) {
                val id = it.arguments?.getLong("foodId") ?: -1L
                DetailScreen(
                    foodId = id,
                    navigateBack = {
                        navController.navigateUp()
                    },
                    navigateToFavorite = {}
                )
            }
        }
        
    }

}

@Composable
fun BottomBar(
    navigationItems: List<NavigationItem>,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    NavigationBar(
        modifier = modifier
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        
        navigationItems.map { item ->
            NavigationBarItem(
                icon = {
                       Icon(
                           imageVector = item.icon, 
                           contentDescription = item.title
                       )
                },
                label= { Text(text = item.title) }, 
                selected = currentRoute == item.screen.route, 
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
    }

}

private fun toCreatorPage(context: Context, url: String) {

    val webPage = Uri.parse(url)
    val intent = Intent(Intent.ACTION_VIEW, webPage)

    context.startActivity(
        Intent.createChooser(
            intent,
            context.getString(R.string.pick_a_browser)
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun FoodsAppPreview() {
    SubmissionTheme {
        FoodsApp()
    }
}