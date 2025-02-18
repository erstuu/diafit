package com.health.diafit.data.remote

import com.health.diafit.data.remote.response.UserHistoriesResponse
import com.health.diafit.data.remote.request.UserInputProfile
import com.health.diafit.data.remote.request.UserLoginRequest
import com.health.diafit.data.remote.response.UserLoginResponse
import com.health.diafit.data.remote.response.UserProfileResponse
import com.health.diafit.data.remote.request.UserRegisterRequest
import com.health.diafit.data.remote.response.UserRegisterResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiService {

    @POST("api/register")
    suspend fun postUserRegister(
        @Body request: UserRegisterRequest
    ): UserRegisterResponse

    @POST("api/login")
    suspend fun postUserLogin(
    @Body request: UserLoginRequest
    ): UserLoginResponse

    @PUT("api/profile")
    suspend fun putUserProfile(
        @Header("Authorization") token: String,
        @Body userData: UserInputProfile
    ): UserProfileResponse

    @GET("api/profile")
    suspend fun getUserProfile(
        @Header("Authorization") token: String
    ): UserProfileResponse

    @GET("api/histories")
    suspend fun getUserHistories(
        @Header("Authorization") token: String
    ): UserHistoriesResponse
}
