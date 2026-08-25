package com.hrushant.jobtrack.ui.auth

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.hrushant.jobtrack.data.repository.AuthRepository
import com.hrushant.jobtrack.databinding.ActivityRegisterBinding
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    private val viewModel: RegisterViewModel by viewModels {
        RegisterViewModelFactory(
            AuthRepository()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupInputFields()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.registrationState.collect { state ->
                    when (state) {
                        is RegistrationState.Idle -> {
                            setLoadingState(isLoading = false)
                        }

                        is RegistrationState.Loading -> {
                            setLoadingState(isLoading = true)
                        }

                        is RegistrationState.Success -> {
                            setLoadingState(isLoading = false)
                            Toast.makeText(
                                this@RegisterActivity,
                                "Registration successful. Please login.",
                                Toast.LENGTH_LONG
                            ).show()

                            // Return to the EXISTING LoginActivity on the backstack
                            finish()
                        }

                        is RegistrationState.Error -> {
                            setLoadingState(isLoading = false)
                            binding.passwordLayout.error = state.message
                        }
                    }
                }
            }
        }

        binding.btnRegister.setOnClickListener {
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
                viewModel.registerUser(email, password)
            }
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
        binding.btnRegister.isEnabled = !isLoading
        binding.etEmail.isEnabled = !isLoading
        binding.etPassword.isEnabled = !isLoading
    }
}