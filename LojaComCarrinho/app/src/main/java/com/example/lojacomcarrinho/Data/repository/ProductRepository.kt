package com.example.lojacomcarrinho.Data.repository


import com.example.lojacomcarrinho.Domain.model.Product
import com.google.firebase.firestore.FirebaseFirestore


class ProductRepository(private val firestore: FirebaseFirestore) {

    fun fetchProducts(onComplete: (List<Product>, String?) -> Unit) {
        firestore.collection("products")
            .get()
            .addOnSuccessListener { documents ->
                val productList = documents.mapNotNull { document ->
                    document.toObject(Product::class.java).apply { id = document.id }
                }
                onComplete(productList, null)
            }
            .addOnFailureListener { exception ->
                onComplete(emptyList(), exception.localizedMessage)
            }
    }

    fun addProduct(product: Product, onComplete: (Boolean, String?) -> Unit) {
        firestore.collection("products")
            .add(product)
            .addOnSuccessListener {
                onComplete(true, null)
            }
            .addOnFailureListener { exception ->
                onComplete(false, exception.localizedMessage)
            }
    }
}
