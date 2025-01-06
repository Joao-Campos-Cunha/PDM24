package com.example.lojacomcarrinho.Data.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class FirebaseAuthService(private val auth: FirebaseAuth = FirebaseAuth.getInstance()) {

    // Obter o utilizador atualmente autenticado
    val currentUser: FirebaseUser?
        get() = auth.currentUser

    // Registar um novo utilizador
    suspend fun registerUser(email: String, password: String): FirebaseUser? {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            result.user
        } catch (e: Exception) {
            throw e
        }
    }

    // Login de utilizador
    suspend fun loginUser(email: String, password: String): FirebaseUser? {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            result.user
        } catch (e: Exception) {
            throw e
        }
    }

    // Logout do utilizador
    fun logoutUser() {
        auth.signOut()
    }

    // Reenviar email de verificação
    suspend fun sendEmailVerification() {
        currentUser?.sendEmailVerification()?.await()
    }

    // Reset de palavra-passe
    suspend fun resetPassword(email: String) {
        auth.sendPasswordResetEmail(email).await()
    }
}
