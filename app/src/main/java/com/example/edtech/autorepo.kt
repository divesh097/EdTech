package com.example.edtech

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class autorepo {
    private val auth = FirebaseAuth.getInstance()

    // Check if the user is logged in
    fun isLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    // Register a new user
    suspend fun register(email: String, password: String): Boolean {
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    // Log in a user
    suspend fun login(email: String, password: String): Boolean {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    // Log out the user
    fun logout() {
        auth.signOut()
    }
}
