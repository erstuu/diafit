package com.health.diafit.ui.activity.signup

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.health.diafit.R
import com.health.diafit.util.ProgressDialog
import com.health.diafit.data.ResultState
import com.health.diafit.databinding.ActivitySignupBinding
import com.health.diafit.ui.activity.login.LoginActivity
import com.health.diafit.util.LocaleHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private val progressDialog by lazy { ProgressDialog(this) }
    private val viewModel: SignUpViewModel by viewModels()

    private lateinit var email: TextInputEditText
    private lateinit var username: TextInputEditText
    private lateinit var password: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val language = viewModel.getLanguageSync()
        LocaleHelper.updateLocale(this, language)

        email = binding.tiEmail
        username = binding.tiUsername
        password = binding.tiPassword

        setupAction()
        sendUserDataRegisterRequest()
    }

    private fun sendUserDataRegisterRequest() {
        binding.btnNextSignup.setOnClickListener {
            val email = email.text.toString().trim()
            val username = username.text.toString().trim()
            val password = password.text.toString().trim()

            val emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"

            if (email.isEmpty() || username.isEmpty() || password.isEmpty()) {
                val emptyFields = getString(R.string.all_fields_must_not_be_empty)
                showSnackbar(emptyFields)
            } else if (!email.matches(emailPattern.toRegex())) {
                val emailInvalid = getString(R.string.invalid_email_address)
                showSnackbar(emailInvalid)
            } else {
                viewModel.registerAccount(username, email, password).observe(this) { response ->
                    when (response) {
                        is ResultState.Loading -> {
                            progressDialog.showLoading()
                        }
                        is ResultState.Success -> {
                            progressDialog.hideLoading()
                            showSnackbar(
                                getString(R.string.you_have_successfully_registered)
                                , false
                            )
                            clearTextInput()
                            lifecycleScope.launch {
                                delay(1000)
                                gotoLoginActivity()
                            }
                        }
                        is ResultState.Error -> {
                            progressDialog.hideLoading()
                            showSnackbar(response.error)
                        }
                    }
                }
            }

        }
    }

    private fun clearTextInput() {
        email.text?.clear()
        username.text?.clear()
        password.text?.clear()
    }

    private fun showSnackbar(errorMessage: String, isError: Boolean = true) {
        val snackbar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_SHORT)
        val backgroundColor = if (isError) R.color.red else R.color.army
        val textColor = R.color.white
        snackbar.apply {
            setBackgroundTint(ContextCompat.getColor(this@SignUpActivity, backgroundColor))
            setTextColor(ContextCompat.getColor(this@SignUpActivity, textColor))
            setActionTextColor(ContextCompat.getColor(this@SignUpActivity, textColor))
        }.show()
    }

    private fun gotoLoginActivity() {
        val intent = Intent(this@SignUpActivity, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        startActivity(intent)
    }


    private fun setupAction() {
        binding.login.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            })
            finish()
        }
    }
}