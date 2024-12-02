package com.example.newsapp.presentation.news_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.domain.model.News
import com.example.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NewsDetailViewModel(private val repository: NewsRepository) : ViewModel() {
    private val _newsDetail = MutableStateFlow<News?>(null)
    val newsDetail: StateFlow<News?> = _newsDetail

    fun fetchNewsDetail(title: String) {
        viewModelScope.launch {
            try {
                // carrega notícia pelo título
                val news = repository.getNews("technology").find { it.title == title }
                _newsDetail.value = news
            } catch (e: Exception) {
                println("Erro ao carregar detalhes da notícia: ${e.message}")
                _newsDetail.value = null
            }
        }
    }
}
