package com.example.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.newsapp.data.remote.api.RetrofitInstance
import com.example.newsapp.data.repository.NewsRepositoryImpl
import com.example.newsapp.domain.model.News
import com.example.newsapp.domain.repository.NewsRepository
import com.example.newsapp.presentation.news_detail.NewsDetailScreen
import com.example.newsapp.presentation.news_list.NewsListScreen
import com.example.newsapp.presentation.news_list.NewsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val repository = NewsRepositoryImpl(RetrofitInstance.api)
            NewsApp(repository)
        }
    }
}

@Composable
fun NewsApp(repository: NewsRepository) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "news_list"
    ) {
        // Tela de Listagem de Notícias
        composable("news_list") {
            val viewModel = NewsViewModel(repository)
            NewsListScreen(
                viewModel = viewModel,
                onNewsClick = { news ->
                    navController.navigate("news_detail/${news.title}") // `news.title` deve existir
                }
            )
        }

        // Tela de Detalhes de uma Notícia
        composable(
            route = "news_detail/{newsTitle}",
            arguments = listOf(navArgument("newsTitle") { type = NavType.StringType })
        ) { backStackEntry ->
            val newsTitle = backStackEntry.arguments?.getString("newsTitle") ?: ""
            val dummyNews = News(
                title = newsTitle,
                description = "Description of $newsTitle",
                imageUrl = "",
                link = ""
            )
            NewsDetailScreen(news = dummyNews)
        }
    }
}

