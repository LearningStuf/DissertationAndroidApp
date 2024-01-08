package com.example.frauddetectionlibrary

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FraudPaymentActivity : AppCompatActivity() {


    val amountKey = "amount"
    val categoryKey = "category"
    val merchantIdKey = "merchant"
    val usernameIdKey = "username"
    val apiResponseKey = "api_response"

    val retrofitBuilder = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("http://10.0.2.2:8000/")
        .build()
        .create(ApiService::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fraud_payment)

        val amount = intent.getStringExtra(amountKey)!!.toFloat()
        val category = intent.getStringExtra(categoryKey).toString()
        val merchant = intent.getStringExtra(merchantIdKey).toString()
//        val username = intent.getStringExtra(usernameIdKey).toString()

        val sharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE)


        val username = sharedPreferences.getString("username", "null")


        val requestBody = FraudBody(amount,category,merchant, username!!)
        val retrofitData = retrofitBuilder.fraudPrediction(requestBody)
        retrofitData.enqueue(object : Callback<FraudResponse?> {
            override fun onResponse(
                call: Call<FraudResponse?>,
                response: Response<FraudResponse?>
            ) {
                if(response.code().equals(200)){
                    if(response.body()?.fraud == 1){
                        intent.putExtra(apiResponseKey, "Success")
                        setResult(RESULT_OK,intent)
                        finish()
                    }
                    else {
                        showSetPassDialog()
                    }


                }
            }

            override fun onFailure(call: Call<FraudResponse?>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })

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

    private fun  showSetPassDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Need password fraudulent activity detected")

        val input = EditText(this)
        input.hint = "Enter your password"
        builder.setView(input)

        builder.setPositiveButton("Ok") { dialog, _ ->
            val enteredPassword = input.text.toString()
            // Handle the entered PIN here
            if (enteredPassword.isNotBlank()) {
                performLoginUserPassword(enteredPassword)
            } else {
                showAlert("Error", "You have entered an incorrect password")
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

    private fun performLoginUserPassword (password: String) {

//        val username = intent.getStringExtra(usernameKey).toString()
        val sharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE)


        val username = sharedPreferences.getString("username", "null")
        val requestBody = LoginUserBody(
            username!!,
            password
        )
        val retrofitData = retrofitBuilder.loginWithPassword(requestBody)
        retrofitData.enqueue(object : Callback<LoginUserResponse?> {
            override fun onResponse(
                call: Call<LoginUserResponse?>,
                response: Response<LoginUserResponse?>
            ) {
                if(response.code().equals(200)){
                    intent.putExtra(apiResponseKey, "Success")
                    setResult(RESULT_OK,intent)
                    finish()
                }

                else {
                    intent.putExtra(apiResponseKey, "Failed")
                    setResult(RESULT_CANCELED,intent)
                    finish()
                }
            }

            override fun onFailure(call: Call<LoginUserResponse?>, t: Throwable) {
                intent.putExtra(apiResponseKey, "Failed")
                setResult(RESULT_CANCELED,intent)
                finish()
            }
        })

    }

}