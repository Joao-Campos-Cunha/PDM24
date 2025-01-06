package com.example.lojacomcarrinho.Data.repository


import com.google.firebase.firestore.FirebaseFirestore
import com.example.lojacomcarrinho.Domain.model.CartItem

class CartRepository(private val firestore: FirebaseFirestore) {

    fun fetchCart(userId: String, onComplete: (List<CartItem>, String?) -> Unit) {
        firestore.collection("carts").document(userId).collection("items")
            .get()
            .addOnSuccessListener { documents ->
                val cartItems = documents.mapNotNull { document ->
                    document.toObject(CartItem::class.java).apply { id = document.id }
                }
                onComplete(cartItems, null)
            }
            .addOnFailureListener { exception ->
                onComplete(emptyList(), exception.localizedMessage)
            }
    }

    fun addItemToCart(userId: String, cartItem: CartItem, onComplete: (Boolean, String?) -> Unit) {
        firestore.collection("carts").document(userId).collection("items")
            .add(cartItem)
            .addOnSuccessListener {
                onComplete(true, null)
            }
            .addOnFailureListener { exception ->
                onComplete(false, exception.localizedMessage)
            }
    }

    fun shareCart(userId: String, sharedWithUserId: String, onComplete: (Boolean, String?) -> Unit) {
        val cartRef = firestore.collection("carts").document(userId).collection("items")
        cartRef.get()
            .addOnSuccessListener { documents ->
                val sharedCartRef = firestore.collection("carts").document(sharedWithUserId).collection("items")
                firestore.runBatch { batch ->
                    documents.forEach { document ->
                        val cartItem = document.toObject(CartItem::class.java)
                        batch.set(sharedCartRef.document(), cartItem)
                    }
                }.addOnSuccessListener {
                    onComplete(true, null)
                }.addOnFailureListener { exception ->
                    onComplete(false, exception.localizedMessage)
                }
            }
            .addOnFailureListener { exception ->
                onComplete(false, exception.localizedMessage)
            }
    }
    fun removeItemFromCart(userId: String, cartItem: CartItem, onComplete: (Boolean, String?) -> Unit) {
        firestore.collection("carts").document(userId).collection("items")
            .document(cartItem.id)
            .delete()
            .addOnSuccessListener {
                onComplete(true, null)
            }
            .addOnFailureListener { exception ->
                onComplete(false, exception.message)
            }
    }

}

