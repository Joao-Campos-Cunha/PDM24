package com.example.newsapp.presentation.news_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.domain.model.News
import com.example.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NewsViewModel(private val repository: NewsRepository) : ViewModel() {
    // Estado das notícias
    private val _news = MutableStateFlow<List<News>>(emptyList())
    val news: StateFlow<List<News>> = _news

    // Estado de carregamento
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // Função para buscar notícias
    fun fetchNews(section: String) {
        viewModelScope.launch {
            _isLoading.value = true // Inicia o estado de carregamento
            try {
                _news.value = repository.getNews(section) // Busca as notícias
            } catch (e: Exception) {
                // Logar o erro (opcional)
                println("Erro ao buscar notícias: ${e.message}")
                _news.value = emptyList() // Atualiza com lista vazia em caso de erro
            } finally {
                _isLoading.value = false // Finaliza o estado de carregamento
            }
        }
    }
}
