package com.example.lojacomcarrinho.Presentation.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lojacomcarrinho.Domain.usecase.RegisterUseCase

class RegisterViewModel(private val registerUseCase: RegisterUseCase) : ViewModel() {

    private val _registerSuccess = MutableLiveData<Boolean>()
    val registerSuccess: LiveData<Boolean> get() = _registerSuccess

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun register(email: String, password: String, confirmPassword: String) {
        registerUseCase.execute(email, password, confirmPassword) { success, errorMessage ->
            if (success) {
                _registerSuccess.postValue(true)
            } else {
                _error.postValue(errorMessage)
            }
        }
    }
}
