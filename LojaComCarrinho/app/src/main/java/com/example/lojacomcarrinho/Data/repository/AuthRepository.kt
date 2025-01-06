package com.example.lojacomcarrinho.Data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthRepository(private val firebaseAuth: FirebaseAuth) {

    fun getCurrentUser(): FirebaseUser? = firebaseAuth.currentUser

    fun login(email: String, password: String, onComplete: (Boolean, String?) -> Unit) {
        if (email.isBlank() || password.isBlank()) {
            onComplete(false, "Email ou senha não podem estar em branco.")
            return
        }
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(true, null)
                } else {
                    onComplete(false, task.exception?.localizedMessage)
                }
            }
    }

    fun register(email: String, password: String, onComplete: (Boolean, String?) -> Unit) {
        if (email.isBlank() || password.length < 6) {
            onComplete(false, "Senha deve ter pelo menos 6 caracteres.")
            return
        }
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(true, null)
                } else {
                    onComplete(false, task.exception?.localizedMessage)
                }
            }
    }

    fun logout(onComplete: (Boolean, String?) -> Unit) {
        try {
            firebaseAuth.signOut()
            onComplete(true, null)
        } catch (e: Exception) {
            onComplete(false, e.message)
        }
    }
}
