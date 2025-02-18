package com.health.diafit.data.remote.request

data class UserLoginRequest(
    val email: String,
    val password: String,
    val isLogin: Boolean = false
)
