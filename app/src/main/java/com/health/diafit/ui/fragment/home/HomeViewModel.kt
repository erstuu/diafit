package com.health.diafit.ui.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.health.diafit.data.UserRepository
import com.health.diafit.data.remote.response.UserSession
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    fun getSession(): LiveData<UserSession> {
        return userRepository.getSession().asLiveData()
    }
}