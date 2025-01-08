package com.example.lojacomcarrinho.Presentation.ViewModels


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PaymentViewModel : ViewModel() {

    private val _paymentState = MutableLiveData<PaymentState>(PaymentState.Idle)
    val paymentState: LiveData<PaymentState> get() = _paymentState

    fun processPayment(total: Double, method: String) {
        if (method.isBlank() || total <= 0) {
            _paymentState.value = PaymentState.Failure("Método de pagamento inválido ou total inválido.")
            return
        }

        // Simular processamento de pagamento (normalmente, chamaria um UseCase ou Repositório)
        _paymentState.value = PaymentState.Success
    }

    fun resetPaymentState() {
        _paymentState.value = PaymentState.Idle
    }
}

sealed class PaymentState {
    object Idle : PaymentState()
    object Success : PaymentState()
    data class Failure(val errorMessage: String) : PaymentState()
}
