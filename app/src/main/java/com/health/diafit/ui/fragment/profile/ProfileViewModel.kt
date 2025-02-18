package com.health.diafit.ui.fragment.profile

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.health.diafit.R
import com.health.diafit.data.ResultState
import com.health.diafit.data.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    fun saveLanguage(language: String) {
        viewModelScope.launch {
            userRepository.saveLanguage(language)
        }
    }

    fun getLanguage(): LiveData<String> {
        return userRepository.getLanguage()
    }

    fun getSession() = userRepository.getSession().asLiveData()
    fun getUsername() = userRepository.getUsername().asLiveData()

}