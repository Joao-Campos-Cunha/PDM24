package com.example.newsapp.domain.repository

import com.example.newsapp.data.remote.api.NewsApi
import com.example.newsapp.domain.model.News

interface NewsRepository {
    suspend fun getNews(section: String): List<News>
}
