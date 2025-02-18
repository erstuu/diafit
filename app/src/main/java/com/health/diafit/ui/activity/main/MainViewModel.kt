package com.health.diafit.ui.activity.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.health.diafit.data.UserRepository
import com.health.diafit.data.local.UserPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val userPreference: UserPreference,
) : ViewModel() {

    fun getSession() = userRepository.getSession().asLiveData()

    fun getLanguageSync(): String {
        return userPreference.getLanguageSync()
    }

}