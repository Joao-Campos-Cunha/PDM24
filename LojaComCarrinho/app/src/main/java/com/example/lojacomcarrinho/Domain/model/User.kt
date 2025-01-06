package com.example.lojacomcarrinho.Domain.model

data class User(
    var id: String = "", // ID do documento do Firestore
    val name: String = "", // Nome do usuário
    val email: String = "", // Email do usuário
)
