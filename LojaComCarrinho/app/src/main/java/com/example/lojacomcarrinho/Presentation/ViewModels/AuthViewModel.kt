package com.example.lojacomcarrinho.Presentation.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.lojacomcarrinho.Domain.usecase.LoginUseCase

class AuthViewModel(private val loginUseCase: LoginUseCase) : ViewModel() {

    sealed class LoginState {
        object Idle : LoginState()
        object Loading : LoginState()
        object Success : LoginState()
        data class Error(val message: String) : LoginState()
    }

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> get() = _loginState

    fun login(email: String, password: String) {
        _loginState.value = LoginState.Loading
        viewModelScope.launch {
            loginUseCase.execute(email, password) { success, errorMessage ->
                if (success) {
                    _loginState.value = LoginState.Success
                } else {
                    _loginState.value = LoginState.Error(errorMessage ?: "Erro desconhecido")
                }
            }
        }
    }
}
