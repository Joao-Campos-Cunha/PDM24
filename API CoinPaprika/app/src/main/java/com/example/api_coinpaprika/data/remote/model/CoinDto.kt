package com.example.api_coinpaprika.data.remote.model

import com.example.api_coinpaprika.domain.model.Coin

data class CoinDto(
    val id: String,
    val name: String,
    val symbol: String,
    val rank: String
) {
    fun toCoin(): Coin {
        return Coin(id = id, name = name, symbol = symbol, rank = rank)
    }
}