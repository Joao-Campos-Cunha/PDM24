package com.example.lojacomcarrinho.Domain.model

data class CartItem(
    var id: String = "", // Propriedade `id` para armazenar o ID do documento
    val productId: String = "",
    val quantity: Int = 0,
    val price: Double = 0.0 // Adiciona a propriedade `price`
)
