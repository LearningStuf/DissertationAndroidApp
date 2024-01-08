package com.example.frauddetectionlibrary

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.provider.Settings


class FraudRegisterActivity : AppCompatActivity() {

    val usernameKey = "username"
    val passwordKey = "password"
    val dateOfBirthKey = "dob"
    val genderKey = "gender"
    val pinKey = "pin"
    val apiResponseKey = "api_response"

    val retrofitBuilder = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("http://10.0.2.2:8000/")
        .build()
        .create(ApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fraud_register_actvity)

        val username = intent.getStringExtra(usernameKey).toString()
        val password = intent.getStringExtra(passwordKey).toString()
        val dateOfBirth = intent.getStringExtra(dateOfBirthKey).toString()
        val gender = intent.getStringExtra(genderKey).toString()
        val pin = intent.getStringExtra(pinKey).toString()
        val uuid = fetchDeviceId()



        val requestBody = RegisterBody(username,password,dateOfBirth,gender,pin,uuid)
        val retrofitData = retrofitBuilder.registerUser(requestBody)
        retrofitData.enqueue(object : Callback<RegisterResponse?> {
            override fun onResponse(
                call: Call<RegisterResponse?>,
                response: Response<RegisterResponse?>
            ) {
                if(response.code().equals(200)){
                    intent.putExtra(apiResponseKey, "Success")
                    setResult(RESULT_OK,intent)
                    finish()
                }

                else{
                    intent.putExtra(apiResponseKey, "Failure")
                    setResult(RESULT_CANCELED,intent)
                    finish()
                }
            }

            override fun onFailure(call: Call<RegisterResponse?>, t: Throwable) {
                intent.putExtra(apiResponseKey, "Failure")
                setResult(RESULT_CANCELED,intent)
                finish()
            }
        })


    }
    @SuppressLint("HardwareIds")
    fun fetchDeviceId(): String {
        return Settings.Secure.getString(
            applicationContext.contentResolver,
            Settings.Secure.ANDROID_ID
        )
    }

}