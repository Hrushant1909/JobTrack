package com.hrushant.jobtrack.data.repository

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.coroutines.tasks.await

class AuthRepository {

    private val firebaseAuth = FirebaseAuth.getInstance()

    suspend fun registerUser(
        email: String,
        password: String
    ): Result<String> {

        return try {
            val result = firebaseAuth
                .createUserWithEmailAndPassword(email, password)
                .await()

            val uid = result.user?.uid

            if (uid != null) {
                // Explicitly sign out the newly created user as per requirements
                firebaseAuth.signOut()
                Result.success(uid)
            } else {
                Result.failure(
                    Exception("User creation succeeded but UID is null")
                )
            }

        } catch (e: Exception) {
            Result.failure(Exception(getFriendlyErrorMessage(e), e))
        }
    }

    suspend fun loginUser(
        email: String,
        password: String
    ): Result<String> {

        return try {
            val result = firebaseAuth
                .signInWithEmailAndPassword(email, password)
                .await()

            val uid = result.user?.uid

            if (uid != null) {
                Result.success(uid)
            } else {
                Result.failure(Exception("UID is null"))
            }

        } catch (e: Exception) {
            Result.failure(Exception(getFriendlyErrorMessage(e), e))
        }
    }

    suspend fun sendPasswordResetEmail(
        email: String
    ): Result<Unit> {

        return try {
            firebaseAuth
                .sendPasswordResetEmail(email)
                .await()

            Result.success(Unit)

        } catch (e: Exception) {
            Result.failure(Exception(getFriendlyErrorMessage(e), e))
        }
    }

    fun logout() {
        firebaseAuth.signOut()
    }

    private fun getFriendlyErrorMessage(e: Throwable): String {
        return when (e) {
            is FirebaseAuthInvalidUserException -> "No account exists with this email address."
            is FirebaseAuthInvalidCredentialsException -> "Invalid email address or incorrect password."
            is FirebaseAuthUserCollisionException -> "This email address is already registered."
            is FirebaseAuthWeakPasswordException -> e.reason ?: "Password is too weak. Must be at least 6 characters."
            is FirebaseNetworkException -> "Network error. Please check your internet connection."
            else -> e.localizedMessage ?: "An error occurred during authentication."
        }
    }
}