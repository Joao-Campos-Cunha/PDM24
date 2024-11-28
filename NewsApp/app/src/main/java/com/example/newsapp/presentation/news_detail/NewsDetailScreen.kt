package com.example.newsapp.presentation.news_detail

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.example.newsapp.domain.model.News

@Composable
fun NewsDetailScreen(news: News) {
    Column {
        Text(text = news.title)
        Text(text = news.description)
    }
}
