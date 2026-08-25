package com.hrushant.jobtrack.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.hrushant.jobtrack.MainActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()

        val currentUser = firebaseAuth.currentUser

        if (currentUser != null) {
            startActivity(
                Intent(this, MainActivity::class.java)
            )
        } else {
            startActivity(
                Intent(this, LoginActivity::class.java)
            )
        }

        finish()
    }
}