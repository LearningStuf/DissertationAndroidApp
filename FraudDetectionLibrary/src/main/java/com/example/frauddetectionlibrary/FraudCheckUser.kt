package com.example.frauddetectionlibrary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FraudCheckUser : AppCompatActivity() {


    val retrofitBuilder = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("http://10.0.2.2:8000/")
        .build()
        .create(ApiService::class.java)

    val usernameKey = "username"
    val apiResponseKey = "check_user"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fraud_check_user)

//        val intent = Intent()
        val username = intent.getStringExtra(usernameKey).toString()

        val requestBody = CheckUserBody(username)
        val retrofitData = retrofitBuilder.checkUser(requestBody)
        retrofitData.enqueue(object : Callback<CheckUserResponse?> {
            override fun onResponse(
                call: Call<CheckUserResponse?>,
                response: Response<CheckUserResponse?>
            ) {
                if(response.code().equals(200)){
//                    intent.putExtra(apiResponseKey, response.body()?.result.toString())
//                    setResult(RESULT_OK,intent)
//                    finish()
                    showSetPinDialog()

                }
                else{
                    intent.putExtra(apiResponseKey, "Invalid Username")
                    setResult(RESULT_CANCELED,intent)
                    finish()
                }
            }

            override fun onFailure(call: Call<CheckUserResponse?>, t: Throwable) {
                intent.putExtra(apiResponseKey, "Failed")
                setResult(RESULT_CANCELED,intent)
                finish()
            }
        })


    }


    private fun  showSetPinDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Set Secure PIN")

        val input = EditText(this)
        input.hint = "Enter your PIN"
        builder.setView(input)

        builder.setPositiveButton("Set") { dialog, _ ->
            val enteredPin = input.text.toString()
            // Handle the entered PIN here
            if (enteredPin.isNotBlank()) {
                performLoginUser(enteredPin)
            } else {
                showAlert("Error", "You have entered an incorrect pin")
            }
            dialog.dismiss()
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            // Handle cancel action if needed
            dialog.dismiss()
        }

        builder.setIcon(android.R.drawable.ic_dialog_info)
        builder.show()
        Log.i(
            "Alert Dialog",
            "Lets see what happens"
        )


    }

    private fun showAlert(title: String, message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()

    }

    private fun performLoginUser(pin: String) {

        val username = intent.getStringExtra(usernameKey).toString()

        val requestBody = CheckUserPinBody(
            username,
            pin
        )
        val retrofitData = retrofitBuilder.loginWithPin(requestBody)
        retrofitData.enqueue(object : Callback<CheckUserPinResponse?> {
            override fun onResponse(
                call: Call<CheckUserPinResponse?>,
                response: Response<CheckUserPinResponse?>
            ) {
                if(response.code().equals(200)){
                    intent.putExtra(apiResponseKey, response.body()?.result.toString())
                    setResult(RESULT_OK,intent)
                    finish()

                }
                else{
                    intent.putExtra(apiResponseKey, "Incorrect Pin")
                    setResult(RESULT_CANCELED,intent)
                    finish()
                }
            }

            override fun onFailure(call: Call<CheckUserPinResponse?>, t: Throwable) {
                intent.putExtra(apiResponseKey, "Failed")
                setResult(RESULT_CANCELED,intent)
                finish()
            }

        })

    }
}