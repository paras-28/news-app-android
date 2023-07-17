package com.example.newsapp.ui.theme
import com.example.newsapp.MockData
import com.example.newsapp.ui.theme.screen.DetailScreen
import com.example.newsapp.ui.theme.screen.TopNews
import com.example.newsapp.BottomMenuScreen
import com.example.newsapp.components.BottomMenu

import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.network.NewsManager
import com.example.newsapp.network.models.TopNewsArticle
import com.example.newsapp.ui.theme.screen.Categories
import com.example.newsapp.ui.theme.screen.Sources

@Composable
fun NewsApp() {
    val scrollState = rememberScrollState()
    val navController = rememberNavController()

    MainScreen(navController = navController,scrollState)
}

@Composable
fun MainScreen(navController: NavHostController,scrollState: ScrollState) {
    Scaffold(bottomBar ={
        BottomMenu(navController = navController)
    }) {
        Navigation(navController =navController , scrollState =scrollState,paddingValues = it )
    }
}

//Todo 9: create a padding value variable and pass into NavHost modifier
@Composable
fun Navigation(navController:NavHostController, scrollState: ScrollState, newsManager: NewsManager= NewsManager(), paddingValues: PaddingValues) {
    val articles = newsManager.newsResponse.value.articles
    Log.d("newss","$articles")
    articles?.let {
        NavHost(navController = navController,
            startDestination =BottomMenuScreen.TopNews.route,
            modifier = Modifier.padding(paddingValues)) {
            //Todo 7:pass articles to bottomNavigation
            bottomNavigation(navController = navController, articles)
            //Todo 12: replace the key with index and get article by selected index
            composable("Detail/{index}",
                arguments = listOf(
                    navArgument("index") { type = NavType.IntType }
                )) { navBackStackEntry ->
                val index = navBackStackEntry.arguments?.getInt("index")
                index?.let {
                    val article = articles[index]
                    DetailScreen(article, scrollState, navController)
                }
            }
        }
    }
}

//Todo 6: create TopNews list and provide the value to TopNews composable
fun NavGraphBuilder.bottomNavigation(navController: NavController,articles:List<TopNewsArticle>) {
    composable(BottomMenuScreen.TopNews.route) {
        TopNews(navController = navController,articles)
    }
    composable(BottomMenuScreen.Categories.route) {
        Categories()
    }
    composable(BottomMenuScreen.Sources.route) {
        Sources()
    }
}