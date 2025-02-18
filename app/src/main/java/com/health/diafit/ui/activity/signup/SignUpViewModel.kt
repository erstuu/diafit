package com.health.diafit.ui.activity.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.health.diafit.data.ResultState
import com.health.diafit.data.UserRepository
import com.health.diafit.data.local.UserPreference
import com.health.diafit.data.remote.request.UserRegisterRequest
import com.health.diafit.data.remote.response.UserRegisterResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val userPreference: UserPreference
) : ViewModel() {

    fun registerAccount(name: String, email: String, password: String): LiveData<ResultState<UserRegisterResponse>> {
        val request = UserRegisterRequest(name, email, password)

        return userRepository.registerUser(request)
    }

    fun getLanguageSync(): String {
        return userPreference.getLanguageSync()
    }
}