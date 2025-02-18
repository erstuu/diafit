package com.health.diafit.data.remote.request

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserData(
    var name : String? = null,
    var age: Int? = null,
    var hypertension: String? = null,
    var heartDisease: String? = null,
    var bmi: Float? = null,
    var hbA1cLevel: Float? = null,
    var bloodGlucoseLevel: Int? = null,
    var gender: String? = null,
    var smokingHistory: String? = null
) : Parcelable