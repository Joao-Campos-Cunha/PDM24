package com.example.newsapp.domain.model


import java.util.Date

data class News(
    val title: String,
    val description: String,
    val imageUrl: String,
    val link: String,
    val author: String,
    val tipo: String
)

