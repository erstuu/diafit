package com.health.diafit.data.remote.request

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserDataPredict(
    var name: String? = null,
    var age: Int? = null,
    var hypertension: String? = null,
    var heartDisease: String? = null,
    var bodyMassIndex: Float? = null,
    var hemoglobinLevel: Float? = null,
    var glucoseLevel: Int? = null,
    var gender: String? = null,
    var smokingHistory: String? = null,
    var description: String? = null
) : Parcelable