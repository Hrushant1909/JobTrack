package com.hrushant.jobtrack.ui.auth

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.hrushant.jobtrack.MainActivity
import com.hrushant.jobtrack.data.repository.AuthRepository
import com.hrushant.jobtrack.databinding.ActivityLoginBinding
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val viewModel: LoginViewModel by viewModels {
        LoginViewModelFactory(
            AuthRepository()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->

            val systemBars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars()
            )

            view.setPadding(
                view.paddingLeft,
                systemBars.top,
                view.paddingRight,
                view.paddingBottom
            )

            insets
        }
        // Clear error text when inputs are modified
        setupInputFields()

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString()

            var isValid = true

            if (email.isEmpty()) {
                binding.emailLayout.error = "Email cannot be empty"
                isValid = false
            } else {
                binding.emailLayout.error = null
            }

            if (password.isEmpty()) {
                binding.passwordLayout.error = "Password cannot be empty"
                isValid = false
            } else {
                binding.passwordLayout.error = null
            }

            if (isValid) {
                viewModel.loginUser(email, password)
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginState.collect { state ->
                    when (state) {
                        is LoginState.Idle -> {
                            setLoadingState(isLoading = false)
                        }

                        is LoginState.Loading -> {
                            setLoadingState(isLoading = true)
                        }

                        is LoginState.Success -> {
                            setLoadingState(isLoading = false)
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }

                        is LoginState.Error -> {
                            setLoadingState(isLoading = false)
                            binding.passwordLayout.error = state.message
                        }

                        is LoginState.PasswordResetSent -> {
                            setLoadingState(isLoading = false)
                            Toast.makeText(
                                this@LoginActivity,
                                "Password reset email sent. Please check your inbox.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }

        binding.tvForgotPassword.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()

            if (email.isEmpty()) {
                binding.emailLayout.error = "Enter your email first"
            } else {
                binding.emailLayout.error = null
                viewModel.sendPasswordResetEmail(email)
            }
        }

        binding.tvRegister.setOnClickListener {
            startActivity(
                Intent(this, RegisterActivity::class.java)
            )
        }
    }

    private fun setupInputFields() {
        binding.etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.emailLayout.error = null
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.passwordLayout.error = null
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setLoadingState(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.btnLogin.isEnabled = !isLoading
        binding.tvForgotPassword.isEnabled = !isLoading
        binding.tvRegister.isEnabled = !isLoading
        binding.etEmail.isEnabled = !isLoading
        binding.etPassword.isEnabled = !isLoading
    }
}