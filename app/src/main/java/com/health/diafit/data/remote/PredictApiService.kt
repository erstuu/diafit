package com.health.diafit.data.remote

import com.health.diafit.data.remote.request.UserData
import com.health.diafit.data.remote.response.UserPredictResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface PredictApiService {
    @POST("api/predict")
    suspend fun postUserPredict(
        @Body userData: UserData
    ): UserPredictResponse
}