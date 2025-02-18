package com.health.diafit.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserPredictResponse(

	@field:SerializedName("input")
	val input: Input,

	@field:SerializedName("description")
	val description: String
)

data class Input(

	@field:SerializedName("blood_glucose_level")
	val blood_glucose_level: Int,

	@field:SerializedName("smoking_history_encoded")
	val smoking_history_encoded: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("gender_encoded")
	val gender_encoded: String,

	@field:SerializedName("heart_disease")
	val heart_disease: String,

	@field:SerializedName("hypertension")
	val hypertension: String,

	@field:SerializedName("HbA1c_level")
	val HbA1c_level: Float,

	@field:SerializedName("age")
	val age: Int,

	@field:SerializedName("bmi")
	val bmi: Int
)
