package com.example.lojacomcarrinho.Presentation.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lojacomcarrinho.Domain.usecase.LoginUseCase

class AuthViewModel(private val loginUseCase: LoginUseCase) : ViewModel() {

    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> get() = _loginResult

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun login(email: String, password: String) {
        loginUseCase.execute(email, password) { success, errorMessage ->
            if (success) {
                _loginResult.postValue(true)
            } else {
                _loginResult.postValue(false)
                _error.postValue(errorMessage)
            }
        }
    }
}
