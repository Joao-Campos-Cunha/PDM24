package com.example.api_coinpaprika.domain.model

// Detalhes especificos de uma moeda em especifico
data class CoinDetail(
    val id: String,
    val name: String,
    val description: String,
    val rank: String,
    val logo: String
)