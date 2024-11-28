package com.example.newsapp.presentation.news_list

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.newsapp.domain.model.News

@Composable
fun NewsListScreen(
    viewModel: NewsViewModel,
    onNewsClick: (News) -> Unit // Certifique-se de que este parâmetro existe
) {
    val newsList by viewModel.news.collectAsState()

    LazyColumn {
        items(newsList) { news ->
            NewsItem(news = news, onClick = { onNewsClick(news) })
        }
    }
}

@Composable
fun NewsItem(news: News) {
    Text(text = news.title)
    Text(text = news.description)
}
