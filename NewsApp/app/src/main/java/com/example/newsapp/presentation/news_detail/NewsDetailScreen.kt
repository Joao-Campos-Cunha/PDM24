package com.example.newsapp.presentation.news_detail

import androidx.compose.foundation.background
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import com.example.newsapp.domain.model.News

@Composable
fun NewsDetailScreen(
    newsTitle: String,
    viewModel: NewsDetailViewModel
) {
    LaunchedEffect(Unit) {
        viewModel.fetchNewsDetail(newsTitle)
    }

    val newsDetail = viewModel.newsDetail.collectAsState().value
    val backgroundColor = Color(0xFFF5F5F5) // Cinza muito claro

    if (newsDetail == null) {
        Text("Loading details...")
    } else {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
                .background(backgroundColor)
        ) {
            Text(
                text = newsDetail.title,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            Text(
                text = newsDetail.description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = "Read more: ${newsDetail.link}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Blue,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

