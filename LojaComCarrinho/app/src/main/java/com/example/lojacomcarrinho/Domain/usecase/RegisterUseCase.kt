package com.example.lojacomcarrinho.Domain.usecase

import com.example.lojacomcarrinho.Data.repository.AuthRepository

class RegisterUseCase(private val authRepository: AuthRepository) {

    /**
     * Regista um novo utilizador.
     *
     * @param email O email do utilizador.
     * @param password A palavra-passe do utilizador.
     * @param confirmPassword Confirmação da palavra-passe.
     * @param onComplete Callback que retorna sucesso (true) ou falha com mensagem de erro.
     */
    fun execute(email: String, password: String, confirmPassword: String, onComplete: (Boolean, String?) -> Unit) {
        // Validações básicas
        if (email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            onComplete(false, "Todos os campos são obrigatórios.")
            return
        }

        if (password != confirmPassword) {
            onComplete(false, "As palavras-passe não coincidem.")
            return
        }

        if (password.length < 6) {
            onComplete(false, "A palavra-passe deve ter pelo menos 6 caracteres.")
            return
        }

        // Chama o AuthRepository para registar o utilizador
        authRepository.register(email, password) { success, errorMessage ->
            if (success) {
                onComplete(true, null)
            } else {
                onComplete(false, errorMessage)
            }
        }
    }
}
