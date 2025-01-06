package com.example.lojacomcarrinho.Domain.usecase

import com.example.lojacomcarrinho.Data.repository.CartRepository
import com.example.lojacomcarrinho.Domain.model.CartItem

class ManageCartUseCase(private val cartRepository: CartRepository) {

    /**
     * Adiciona um item ao carrinho do utilizador.
     *
     * @param userId ID do utilizador.
     * @param cartItem O item a ser adicionado.
     * @param onComplete Callback que retorna sucesso (true) ou falha com mensagem de erro.
     */
    fun addItem(userId: String, cartItem: CartItem, onComplete: (Boolean, String?) -> Unit) {
        if (cartItem.quantity <= 0) {
            onComplete(false, "A quantidade deve ser maior que 0.")
            return
        }

        cartRepository.addItemToCart(userId, cartItem) { success, errorMessage ->
            onComplete(success, errorMessage)
        }
    }

    /**
     * Busca todos os itens do carrinho de um utilizador.
     *
     * @param userId ID do utilizador.
     * @param onComplete Callback que retorna a lista de itens e mensagem de erro (se existir).
     */
    fun fetchCart(userId: String, onComplete: (List<CartItem>, String?) -> Unit) {
        cartRepository.fetchCart(userId) { items, errorMessage ->
            onComplete(items, errorMessage)
        }
    }

    /**
     * Partilha o carrinho com outro utilizador.
     *
     * @param userId ID do utilizador atual.
     * @param sharedWithUserId ID do utilizador com quem partilhar.
     * @param onComplete Callback que retorna sucesso (true) ou falha com mensagem de erro.
     */
    fun shareCart(userId: String, sharedWithUserId: String, onComplete: (Boolean, String?) -> Unit) {
        if (userId == sharedWithUserId) {
            onComplete(false, "Não é possível partilhar o carrinho consigo mesmo.")
            return
        }

        cartRepository.shareCart(userId, sharedWithUserId) { success, errorMessage ->
            onComplete(success, errorMessage)
        }
    }
    fun removeItem(userId: String, cartItem: CartItem, onComplete: (Boolean, String?) -> Unit) {
        cartRepository.removeItemFromCart(userId, cartItem, onComplete)
    }

}
