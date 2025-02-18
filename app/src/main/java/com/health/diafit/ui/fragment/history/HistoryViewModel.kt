package com.health.diafit.ui.fragment.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.health.diafit.data.ResultState
import com.health.diafit.data.UserRepository
import com.health.diafit.data.remote.response.UserSession
import com.health.diafit.models.HistoryEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private var _listHistories = MutableLiveData<List<HistoryEntity>>()
    val listHistories: LiveData<List<HistoryEntity>> get() = _listHistories

    private var _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun getSession(): LiveData<UserSession> {
        return repository.getSession().asLiveData()
    }

    fun getHistories(token: String) {
        viewModelScope.launch {
            repository.getHistories(token).observeForever{ resultState ->
                when (resultState) {
                    is ResultState.Loading -> {
                        _isLoading.value = true
                    }
                    is ResultState.Success -> {
                        _isLoading.value = false
                        _listHistories.value = resultState.data
                    }
                    is ResultState.Error -> {
                        _isLoading.value = false
                        _errorMessage.value = resultState.error
                    }
                }
            }
        }
    }
}