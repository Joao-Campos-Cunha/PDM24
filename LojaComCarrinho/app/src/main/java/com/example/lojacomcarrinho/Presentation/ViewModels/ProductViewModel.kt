package com.example.lojacomcarrinho.Presentation.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lojacomcarrinho.Domain.model.Product
import com.example.lojacomcarrinho.Domain.usecase.FetchProductsUseCase

class ProductViewModel(private val fetchProductsUseCase: FetchProductsUseCase) : ViewModel() {

    private val _productList = MutableLiveData<List<Product>>()
    val productList: LiveData<List<Product>> get() = _productList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    fun fetchProducts() {
        _isLoading.value = true
        fetchProductsUseCase.execute { products, error ->
            _isLoading.postValue(false)
            if (error == null) {
                _productList.postValue(products)
            } else {
                _errorMessage.postValue(error)
            }
        }
    }
}
