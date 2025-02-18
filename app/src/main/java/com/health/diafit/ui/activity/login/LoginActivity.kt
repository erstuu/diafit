package com.health.diafit.ui.activity.login

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
import com.health.diafit.databinding.ActivityLoginBinding
import com.health.diafit.data.remote.response.UserSession
import com.health.diafit.ui.activity.main.MainActivity
import com.health.diafit.ui.activity.signup.SignUpActivity
import com.health.diafit.util.LocaleHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    private val progressDialog by lazy { ProgressDialog(this) }
    private lateinit var email: TextInputEditText
    private lateinit var password: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val language = viewModel.getLanguageSync()
        LocaleHelper.updateLocale(this, language)

        email = binding.tiEmail
        password = binding.tiPassword

        setupAction()
        sendUserDataLoginRequest()
    }

    private fun setupAction() {
        binding.registerIfHavent.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
            }
            startActivity(intent)
        }
    }

    private fun sendUserDataLoginRequest() {
        binding.btnLogin.setOnClickListener {
            val email = email.text.toString().trim()
            val password = password.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                val emptyFields = getString(R.string.all_fields_must_not_be_empty)
                showSnackbar(emptyFields)
            } else {
                viewModel.loginAccount(email, password).observe(this) { response ->
                    when (response) {
                        is ResultState.Loading -> {
                            progressDialog.showLoading()
                        }
                        is ResultState.Success -> {
                            progressDialog.hideLoading()
                            val user = UserSession(
                                response.data.loginResult?.name.toString(),
                                response.data.loginResult?.token.toString(),
                                true
                            )
                            viewModel.saveSession(user)
                            showSnackbar(getString(R.string.you_have_successfully_login), false)
                            lifecycleScope.launch {
                                delay(1000)
                                goToMainActivity()
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

    private fun showSnackbar(errorMessage: String, isError: Boolean = true) {
        val snackbar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_SHORT)
        val backgroundColor = if (isError) R.color.red else R.color.army
        val textColor = R.color.white
        snackbar.apply {
            setBackgroundTint(ContextCompat.getColor(this@LoginActivity, backgroundColor))
            setTextColor(ContextCompat.getColor(this@LoginActivity, textColor))
            setActionTextColor(ContextCompat.getColor(this@LoginActivity, textColor))
        }.show()
    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        finish()
    }
}