package com.example.newsapp.presentation.news_detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.rememberScrollState
import com.example.newsapp.domain.model.News

@Composable
fun NewsDetailScreen(news: News) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState()) // Permite rolar verticalmente
    ) {
        // Título da notícia
        Text(
            text = news.title,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        // Descrição da notícia
        Text(
            text = news.description,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        // Link para mais detalhes
        Text(
            text = "Read more: ${news.link}",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Blue,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}
