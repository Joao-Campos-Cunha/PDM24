package com.example.lojacomcarrinho.Presentation.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lojacomcarrinho.Domain.model.CartItem
import com.example.lojacomcarrinho.Domain.usecase.ManageCartUseCase

class CartViewModel(private val manageCartUseCase: ManageCartUseCase) : ViewModel() {

    private val _cartItems = MutableLiveData<List<CartItem>>()
    val cartItems: LiveData<List<CartItem>> get() = _cartItems

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    private val _operationSuccess = MutableLiveData<Boolean>()
    val operationSuccess: LiveData<Boolean> get() = _operationSuccess

    private val _totalPrice = MutableLiveData<Double>()
    val totalPrice: LiveData<Double> get() = _totalPrice

    fun fetchCart(userId: String) {
        manageCartUseCase.fetchCart(userId) { items, errorMessage ->
            if (errorMessage == null) {
                _cartItems.postValue(items)
                calculateTotalPrice(items)
            } else {
                _error.postValue(errorMessage)
            }
        }
    }

    fun addItem(userId: String, cartItem: CartItem) {
        manageCartUseCase.addItem(userId, cartItem) { success, errorMessage ->
            if (success) {
                _operationSuccess.postValue(true)
                fetchCart(userId)
            } else {
                _error.postValue(errorMessage)
            }
        }
    }

    fun removeItem(userId: String, cartItem: CartItem) {
        manageCartUseCase.removeItem(userId, cartItem) { success, errorMessage ->
            if (success) {
                _operationSuccess.postValue(true)
                fetchCart(userId)
            } else {
                _error.postValue(errorMessage)
            }
        }
    }

    fun updateQuantity(userId: String, cartItem: CartItem, newQuantity: Int) {
        if (newQuantity < 1) {
            removeItem(userId, cartItem)
        } else {
            val updatedItem = cartItem.copy(quantity = newQuantity)
            addItem(userId, updatedItem)
        }
    }

     fun calculateTotalPrice(cartItems: List<CartItem>) {
        val total = cartItems.sumOf { it.quantity * it.price }
        _totalPrice.postValue(total)
    }




}
