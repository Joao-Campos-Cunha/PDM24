package com.example.api_coinpaprika.domain.use_case

import com.example.api_coinpaprika.data.repository.CoinRepositoryImpl
import com.example.api_coinpaprika.domain.model.Coin

// Caso de Uso para obter lista de moedas utilizando o repositorio
class GetCoinsUseCase(private val repository: CoinRepositoryImpl) {
    suspend operator fun invoke(): List<Coin> {
        return repository.getCoins()
    }
}