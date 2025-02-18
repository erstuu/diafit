package com.health.diafit.data.remote.request

import com.google.gson.annotations.SerializedName

data class UserRegisterRequest(

	@field:SerializedName("name")
	var name: String,

	@field:SerializedName("email")
	var email: String,

	@field:SerializedName("password")
	var password: String,
)
