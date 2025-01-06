package com.example.lojacomcarrinho.Domain.usecase

import com.example.lojacomcarrinho.Data.repository.AuthRepository

class LoginUseCase(private val authRepository: AuthRepository) {

    /**
     * Executa o processo de login com email e password.
     *
     * @param email O email do utilizador.
     * @param password A password do utilizador.
     * @param onComplete Callback que retorna sucesso (true) ou falha com mensagem de erro.
     */
    fun execute(email: String, password: String, onComplete: (Boolean, String?) -> Unit) {
        if (email.isBlank() || password.isBlank()) {
            onComplete(false, "Email e password não podem estar vazios.")
            return
        }

        authRepository.login(email, password) { success, errorMessage ->
            if (success) {
                onComplete(true, null)
            } else {
                onComplete(false, errorMessage ?: "Erro desconhecido ao tentar fazer login.")
            }
        }
    }
}
