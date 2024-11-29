package com.example.newsapp.presentation.news_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.newsapp.domain.model.News

@Composable
fun NewsListScreen(
    viewModel: NewsViewModel,
    onNewsClick: (News) -> Unit
) {
    // Chama fetchNews() ao carregar a tela
    LaunchedEffect(Unit) {
        viewModel.fetchNews("technology") // Substitua "technology" pela seção desejada
    }

    val newsList = viewModel.news.collectAsState().value

    if (newsList.isEmpty()) {
        Text("No news available.") // Exibe mensagem se a lista estiver vazia
    } else {
        LazyColumn {
            items(newsList) { news ->
                NewsItem(news = news, onClick = { onNewsClick(news) })
            }
        }
    }
}


@Composable
fun NewsItem(news: News, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Text(text = news.title, style = MaterialTheme.typography.headlineSmall)
        Text(text = news.description, style = MaterialTheme.typography.bodyMedium)
    }
}
