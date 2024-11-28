package com.example.api_coinpaprika.data.remote.model

import com.example.api_coinpaprika.domain.model.CoinDetail

data class CoinDetailDto(
    val id: String,
    val name: String,
    val description: String,
    val rank: String,
    val logo: String
) {
    fun toCoinDetail(): CoinDetail {
        return CoinDetail(id = id, name = name, description = description, rank = rank, logo = logo )
    }
}