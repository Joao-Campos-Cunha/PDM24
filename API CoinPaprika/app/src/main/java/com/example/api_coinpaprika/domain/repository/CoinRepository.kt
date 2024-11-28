package com.example.api_coinpaprika.domain.repository

import com.example.api_coinpaprika.domain.model.Coin
import com.example.api_coinpaprika.domain.model.CoinDetail

// Repositorio que nao considera a origem dos dados
// (permite utilizar mock data para testes sem aceder a API)
interface CoinRepository {
    suspend fun getCoins(): List<Coin>
    suspend fun getCoinDetail(coinId: String): CoinDetail
}