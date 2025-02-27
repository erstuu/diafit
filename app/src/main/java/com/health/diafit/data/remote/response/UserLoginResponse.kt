package com.health.diafit.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserLoginResponse(

    @field:SerializedName("loginResult")
	val loginResult: LoginResult? = null,

    @field:SerializedName("error")
	val error: Boolean? = null,

    @field:SerializedName("message")
	val message: String? = null
)

data class LoginResult(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("userId")
	val userId: Int? = null,

	@field:SerializedName("token")
	val token: String? = null
)
