package com.example.lojacomcarrinho.Presentation.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lojacomcarrinho.Domain.model.Product
import com.example.lojacomcarrinho.Domain.usecase.FetchProductsUseCase

class ProductViewModel(private val fetchProductsUseCase: FetchProductsUseCase) : ViewModel() {

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun loadProducts() {
        fetchProductsUseCase.execute { products, errorMessage ->
            if (errorMessage != null) {
                _error.postValue(errorMessage)
            } else {
                _products.postValue(products)
            }
        }
    }
}
