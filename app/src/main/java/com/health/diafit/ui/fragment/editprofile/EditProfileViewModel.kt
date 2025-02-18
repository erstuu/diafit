package com.health.diafit.ui.fragment.editprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.health.diafit.data.ResultState
import com.health.diafit.data.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _usernameState = MutableLiveData<ResultState<String>>()
    val usernameState: LiveData<ResultState<String>> get() = _usernameState

    fun setUsername(username: String) {
        viewModelScope.launch {
            _usernameState.value = ResultState.Loading
            _usernameState.value = userRepository.setUsername(username)
        }
    }

    fun clearUserSession() {
        viewModelScope.launch {
            userRepository.clearSession()
        }
    }
}