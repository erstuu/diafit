package com.health.diafit.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserRegisterResponse(

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)
