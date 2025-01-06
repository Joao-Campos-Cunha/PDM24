package com.example.lojacomcarrinho.Data.remote

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirestoreService(private val db: FirebaseFirestore = FirebaseFirestore.getInstance()) {

    // Add a document to a collection
    suspend fun <T : Any> addDocument(collectionPath: String, document: T): String {
        return try {
            val result = db.collection(collectionPath).add(document).await()
            result.id
        } catch (e: Exception) {
            throw e
        }
    }

    // Update an existing document
    suspend fun updateDocument(collectionPath: String, documentId: String, updates: Map<String, Any>) {
        try {
            db.collection(collectionPath).document(documentId).update(updates).await()
        } catch (e: Exception) {
            throw e
        }
    }

    // Get a specific document
    suspend fun <T : Any> getDocument(collectionPath: String, documentId: String, clazz: Class<T>): T? {
        return try {
            val snapshot = db.collection(collectionPath).document(documentId).get().await()
            snapshot.toObject(clazz)
        } catch (e: Exception) {
            throw e
        }
    }

    // Get all documents from a collection
    suspend fun <T : Any> getAllDocuments(collectionPath: String, clazz: Class<T>): List<T> {
        return try {
            val snapshot = db.collection(collectionPath).get().await()
            snapshot.documents.mapNotNull { it.toObject(clazz) }
        } catch (e: Exception) {
            throw e
        }
    }

    // Delete a document
    suspend fun deleteDocument(collectionPath: String, documentId: String) {
        try {
            db.collection(collectionPath).document(documentId).delete().await()
        } catch (e: Exception) {
            throw e
        }
    }
}

