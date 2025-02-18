package com.health.diafit.data.remote.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserSession(
    val name: String = "",
    val token: String? = "",
    val isLogin: Boolean = false
) : Parcelable
