package com.example.newsapp.presentation.news_list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsapp.domain.model.News

@Composable
fun NewsListScreen(
    viewModel: NewsViewModel,
    onNewsClick: (News) -> Unit
) {
    // Chama fetchNews() ao carregar a tela
    LaunchedEffect(Unit) {
        viewModel.fetchNews("technology")
    }

    val newsList = viewModel.news.collectAsState().value
    val backgroundColor = Color(0xFFF5F5F5) // Cinza muito claro


    Column {
        // Título da Tela
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor)
                .border(BorderStroke(1.dp, Color.LightGray))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "NewsApp",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontSize = 30.sp
            )
        }

        if (newsList.isEmpty()) {
            Text(
                text = "No news available.",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                items(newsList) { news ->
                    NewsItem(
                        news = news,
                        onClick = { onNewsClick(news) }
                    )
                    Divider(color = Color.Gray, thickness = 1.dp) // Linha separadora entre notícias
                }
            }
        }
    }
}

@Composable
fun NewsItem(news: News, onClick: () -> Unit) {

    val backgroundColor = Color(0xFFF5F5F5) // Cinza muito claro

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .border(BorderStroke(1.dp, Color.LightGray)) // Borda ao redor do item
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.clickable { onClick() }) {
            // Título da Notícia
            Text(
                text = news.title,
                style = MaterialTheme.typography.titleMedium,
                fontSize = 18.sp,
                color = Color.DarkGray,
                modifier = Modifier.padding(bottom = 8.dp)
            )

        }
    }
}

