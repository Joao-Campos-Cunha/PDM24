package com.example.lojacomcarrinho.Domain.usecase

import com.example.lojacomcarrinho.Data.repository.ProductRepository
import com.example.lojacomcarrinho.Domain.model.Product

class FetchProductsUseCase(private val productRepository: ProductRepository) {

    /**
     * Obtém a lista de produtos.
     *
     * @param onComplete Callback que retorna a lista de produtos ou uma mensagem de erro.
     */
    fun execute(onComplete: (List<Product>, String?) -> Unit) {
        productRepository.fetchProducts { products, errorMessage ->
            if (errorMessage != null) {
                // Retorna erro
                onComplete(emptyList(), errorMessage)
            } else {
                // Retorna os produtos
                onComplete(products, null)
            }
        }
    }
}
