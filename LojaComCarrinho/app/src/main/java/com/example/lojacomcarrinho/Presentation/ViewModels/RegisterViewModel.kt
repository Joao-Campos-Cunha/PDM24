package com.example.lojacomcarrinho.Presentation.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lojacomcarrinho.Domain.usecase.RegisterUseCase

// Define um estado unificado para a UI
sealed class RegisterUiState {
    object Idle : RegisterUiState()
    object Loading : RegisterUiState()
    object Success : RegisterUiState()
    data class Error(val message: String) : RegisterUiState()
}

class RegisterViewModel(private val registerUseCase: RegisterUseCase) : ViewModel() {

    private val _uiState = MutableLiveData<RegisterUiState>(RegisterUiState.Idle)
    val uiState: LiveData<RegisterUiState> get() = _uiState

    // Regista um novo utilizador com validação local e uso do caso de uso
    fun register(email: String, password: String, confirmPassword: String) {
        val validationError = validateInput(email, password, confirmPassword)
        if (validationError != null) {
            _uiState.postValue(RegisterUiState.Error(validationError))
            return
        }

        _uiState.postValue(RegisterUiState.Loading)

        registerUseCase.execute(email, password, confirmPassword) { success, errorMessage ->
            if (success) {
                _uiState.postValue(RegisterUiState.Success)
            } else {
                _uiState.postValue(RegisterUiState.Error(errorMessage ?: "Erro desconhecido"))
            }
        }
    }

    // Validações locais
    private fun validateInput(email: String, password: String, confirmPassword: String): String? {
        if (email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            return "Todos os campos são obrigatórios."
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return "O e-mail fornecido é inválido."
        }
        if (password.length < 6) {
            return "A senha deve ter pelo menos 6 caracteres."
        }
        if (password != confirmPassword) {
            return "As senhas não coincidem."
        }
        return null
    }

    // Reseta o estado da UI
    fun resetState() {
        _uiState.postValue(RegisterUiState.Idle)
    }
}
