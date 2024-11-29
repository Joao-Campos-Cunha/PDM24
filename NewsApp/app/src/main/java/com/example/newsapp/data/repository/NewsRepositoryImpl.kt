package com.example.newsapp.data.repository

import com.example.newsapp.data.remote.api.NewsApi
import com.example.newsapp.domain.model.News
import com.example.newsapp.domain.repository.NewsRepository

class NewsRepositoryImpl(private val api: NewsApi) : NewsRepository {
    override suspend fun getNews(section: String): List<News> {
        val response = api.getTopStories(section, "dAKO3KZi9qznSKZFG8uJljpI9aiQRA04")
        println("Response: ${response.body()}") // Verifique se os dados são retornados corretamente
        if (response.isSuccessful) {
            return response.body()?.results?.map { dto ->
                News(
                    title = dto.title,
                    description = dto.abstract,
                    imageUrl = dto.multimedia.firstOrNull()?.url ?: "",
                    link = dto.url
                )
            } ?: emptyList()
        } else {
            // Log de erro ou tratamento de exceção
            println("Erro ao buscar notícias: ${response.errorBody()?.string()}")
            return emptyList()
        }
    }
}
