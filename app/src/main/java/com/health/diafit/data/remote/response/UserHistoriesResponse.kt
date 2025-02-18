package com.health.diafit.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserHistoriesResponse(

	@field:SerializedName("histories")
	val histories: List<HistoriesItem>,

	@field:SerializedName("status")
	val status: String? = null,
)

data class HistoriesItem(

	@field:SerializedName("smoking_history")
	val smokingHistory: String? = null,

	@field:SerializedName("diabetes_category")
	val diabetesCategory: String? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("blood_glucose_level")
	val bloodGlucoseLevel: Int? = null,

	@field:SerializedName("check_date")
	val checkDate: String? = null,

	@field:SerializedName("hypertension")
	val hypertension: String? = null,

	@field:SerializedName("heart_disease")
	val heartDisease: String? = null,

	@field:SerializedName("age")
	val age: Int? = null,

	@field:SerializedName("HbA1c_level")
	val hbA1cLevel: Float? = null,

	@field:SerializedName("body_mass_index")
	val bodyMassIndex: Int? = null
)
