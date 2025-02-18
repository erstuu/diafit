package com.health.diafit.ui.activity.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.health.diafit.data.ResultState
import com.health.diafit.data.UserRepository
import com.health.diafit.data.local.UserPreference
import com.health.diafit.data.remote.request.UserLoginRequest
import com.health.diafit.data.remote.response.UserLoginResponse
import com.health.diafit.data.remote.response.UserSession
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val userPreference: UserPreference
) : ViewModel() {

    fun saveSession(user: UserSession) {
        viewModelScope.launch {
            userRepository.saveSession(user)
        }
    }

    fun loginAccount(email: String, password: String): LiveData<ResultState<UserLoginResponse>> {
        val request = UserLoginRequest(email, password)

        return userRepository.loginUser(request)
    }

    fun getLanguageSync(): String {
        return userPreference.getLanguageSync()
    }

}