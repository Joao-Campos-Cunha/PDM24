package com.example.lojacomcarrinho.Domain.usecase

import android.util.Patterns
import com.example.lojacomcarrinho.Data.repository.AuthRepository

class RegisterUseCase(private val authRepository: AuthRepository) {

    fun execute(email: String, password: String, confirmPassword: String, onComplete: (Boolean, String?) -> Unit) {
        if (email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            onComplete(false, "Todos os campos são obrigatórios.")
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            onComplete(false, "O e-mail fornecido é inválido.")
            return
        }

        if (password != confirmPassword) {
            onComplete(false, "As senhas não coincidem.")
            return
        }

        if (password.length < 6) {
            onComplete(false, "A senha deve ter pelo menos 6 caracteres.")
            return
        }

        authRepository.register(email, password) { success, errorMessage ->
            if (success) {
                onComplete(true, null)
            } else {
                onComplete(false, errorMessage)
            }
        }
    }
}
