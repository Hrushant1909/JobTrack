package com.hrushant.jobtrack

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hrushant.jobtrack.data.repository.AuthRepository
import com.hrushant.jobtrack.ui.auth.LoginViewModel
import com.hrushant.jobtrack.ui.auth.LoginViewModelFactory

class MainActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels {
        LoginViewModelFactory(
            AuthRepository()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.nav_host_fragment)) { view, insets ->

            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            view.setPadding(
                view.paddingLeft,
                systemBars.top,
                view.paddingRight,
                view.paddingBottom
            )

            insets
        }

        val navController = findNavController(R.id.nav_host_fragment)

        val bottomNavigation =
            findViewById<BottomNavigationView>(R.id.bottomNavigation)

        bottomNavigation.setupWithNavController(navController)
    }
}