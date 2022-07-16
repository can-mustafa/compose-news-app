package com.mustafacan.dailynews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mustafacan.dailynews.ui.theme.DailyNewsTheme
import com.mustafacan.dailynews.util.Constants.CATEGORY
import com.mustafacan.dailynews.view.NewsCategoryScreen
import com.mustafacan.dailynews.view.NewsListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DailyNewsTheme {

                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "category_list_screen") {
                    composable("category_list_screen") {
                        NewsCategoryScreen(navController = navController)
                    }
                    composable("news_list_screen/{$CATEGORY}",
                        arguments = listOf(navArgument(CATEGORY) {
                            type = NavType.StringType
                        }
                        )) {
                        val category = remember {
                            it.arguments?.getString(CATEGORY)
                        }
                        NewsListScreen(
                            category = category ?: "all"
                        )
                    }
                }
            }
        }
    }
}