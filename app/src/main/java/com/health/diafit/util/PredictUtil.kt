package com.health.diafit.util

import com.health.diafit.data.remote.request.UserData

object PredictUtil {
    fun assessDiabetesRisk(userData: UserData): String {
        val gender = userData.gender
        val age = userData.age
        val hypertension = userData.hypertension
        val heartDisease = userData.heartDisease
        val smokingHistory = userData.smokingHistory
        val bmi = userData.bmi
        val hba1cLevel = userData.hbA1cLevel
        val bloodGlucoseLevel = userData.bloodGlucoseLevel

        if (!validateInputs(gender, age, smokingHistory, heartDisease, hypertension)) {
            throw IllegalArgumentException("Invalid input data")
        }

        // Cek Diabetes Tidak Terkendali
        if ((bloodGlucoseLevel != null && bloodGlucoseLevel > 140) ||
            (hba1cLevel != null && hba1cLevel >= 7.0) ||
            (hypertension == "Yes" && heartDisease == "Yes")) {
            return "Diabetes Tidak Terkendali"
        }

        // Cek Diabetes Terkendali
        if ((bloodGlucoseLevel != null && bloodGlucoseLevel in 126..140) ||
            (hba1cLevel != null && hba1cLevel in 6.5..7.0)) {
            if (hypertension == "No" && heartDisease == "No") {
                return "Diabetes Terkendali"
            }
            return "Diabetes Tidak Terkendali"
        }

        // Cek Pra-diabetes
        if ((bloodGlucoseLevel != null && bloodGlucoseLevel in 100..126) ||
            (hba1cLevel != null && hba1cLevel in 5.7..6.5) ||
            (bmi != null && bmi >= 25)) {
            // Spesifik Gender
            if (gender == "Female" && bmi != null && bmi >= 30) {
                return "Pra-diabetes"
            } else if (gender == "Male" && bmi != null && bmi >= 27) {
                return "Pra-diabetes"
            }
            // Faktor resiko tambahan
            if (age != null && age > 45 ||
                smokingHistory in listOf("current", "former") ||
                hypertension == "Yes") {
                return "Pra-diabetes"
            }
        }

        return "Normal"
    }

    private fun isValidGender(gender: String?): Boolean {
        val validGenders = listOf("Male", "Female")
        return gender != null && gender in validGenders
    }

    private fun isValidAge(age: Int?): Boolean {
        return age != null && age in 1..90
    }

    private fun isValidSmokingHistory(smokingHistory: String?): Boolean {
        val validSmokingHistories = listOf("No info", "never", "former", "current", "not current", "ever")
        return smokingHistory != null && smokingHistory in validSmokingHistories
    }

    private fun isValidHeartDisease(heartDisease: String?): Boolean {
        val validHeartDiseases = listOf("Yes", "No")
        return heartDisease != null && heartDisease in validHeartDiseases
    }

    private fun isValidHypertension(hypertension: String?): Boolean {
        val validHypertensions = listOf("Yes", "No")
        return hypertension != null && hypertension in validHypertensions
    }

    private fun validateInputs(
        gender: String?,
        age: Int?,
        smokingHistory: String?,
        heartDisease: String?,
        hypertension: String?
    ): Boolean {
        return isValidGender(gender) &&
                isValidAge(age) &&
                isValidSmokingHistory(smokingHistory) &&
                isValidHeartDisease(heartDisease) &&
                isValidHypertension(hypertension)
    }
}