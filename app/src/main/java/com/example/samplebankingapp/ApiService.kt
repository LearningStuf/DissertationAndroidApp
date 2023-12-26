package com.example.samplebankingapp

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("health")
    fun healthCheck(): Call<HealthResponse>

    @POST("register")
    fun registerUser(@Body requestBody: RegisterBody): Call<RegisterResponse>

}

data class RegisterResponse (
    @SerializedName("message")
    val message: String
)

data class RegisterBody (
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("dateOfBirth")
    val dateOfBirth: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("pin")
    val pin: String
)

data class HealthResponse (
    @SerializedName("status")
    val status: String
)


