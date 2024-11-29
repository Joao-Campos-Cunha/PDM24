package com.example.newsapp.data.remote.model

data class NewsDto(
    val section: String,
    val title: String,
    val abstract: String,
    val url: String,
    val multimedia: List<MultimediaDto>?
)

data class MultimediaDto(
    val url: String,
    val format: String
)
