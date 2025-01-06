package com.example.lojacomcarrinho.Domain.model

data class Product(
    var id: String = "", // Propriedade para armazenar o ID do documento
    val name: String = "", // Nome do produto
    val description: String = "", // Descrição do produto
    val price: Double = 0.0 // Preço do produto
)
