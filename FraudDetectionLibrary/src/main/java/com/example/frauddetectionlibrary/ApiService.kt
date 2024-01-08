package com.example.frauddetectionlibrary

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

//    @POST("login")
//    fun loginUser(@Body requestBody: LoginBody): Call<LoginResponse>

    @POST("checkUser")
    fun checkUser(@Body requestBody: CheckUserBody): Call<CheckUserResponse>

    @POST("loginWithPin")
    fun loginWithPin(@Body requestBody: CheckUserPinBody): Call<CheckUserPinResponse>

    @POST("login")
    fun loginWithPassword(@Body requestBody: LoginUserBody): Call<LoginUserResponse>

}

data class LoginUserBody (
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val pin: String
)

data class LoginUserResponse (
    @SerializedName("result")
    val result: String
)


data class CheckUserPinBody (
    @SerializedName("username")
    val username: String,
    @SerializedName("pin")
    val pin: String,
    @SerializedName("uuid")
    val uuid: String
)

data class CheckUserPinResponse (
    @SerializedName("result")
    val result: String
)

data class CheckUserBody (
    @SerializedName("username")
    val username: String
)

data class CheckUserResponse (
    @SerializedName("result")
    val result: String
)

data class RegisterResponse (
    @SerializedName("message")
    val message: String
)

data class LoginResponse (
    @SerializedName("token")
    val token: String
)

data class LoginBody (
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String
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
    val pin: String,
    @SerializedName("uuid")
    val uuid: String
)

data class HealthResponse (
    @SerializedName("status")
    val status: String
)


