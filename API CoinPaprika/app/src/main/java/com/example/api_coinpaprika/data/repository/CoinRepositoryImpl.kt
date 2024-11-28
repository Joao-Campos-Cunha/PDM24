package com.example.api_coinpaprika.data.repository

import com.example.api_coinpaprika.data.remote.api.CoinPaprikaApi
import com.example.api_coinpaprika.domain.model.Coin
import com.example.api_coinpaprika.domain.model.CoinDetail
import com.example.api_coinpaprika.domain.repository.CoinRepository


class CoinRepositoryImpl(private val api: CoinPaprikaApi) : CoinRepository {
    override suspend fun getCoins(): List<Coin> {
        return api.getCoins()
            .take(50) // Limits the fetdching to the fitst 10 coins so that the pc wont burn
            .map { it.toCoin() }
    }

    override suspend fun getCoinDetail(coinId: String): CoinDetail {
        return api.getCoinDetail(coinId).toCoinDetail()
    }
}