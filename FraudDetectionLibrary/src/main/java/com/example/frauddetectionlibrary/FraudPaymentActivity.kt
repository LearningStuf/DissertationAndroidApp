package com.example.frauddetectionlibrary

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
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

        val amount = intent.getStringExtra(amountKey).toString()
        val category = intent.getStringExtra(categoryKey).toString()
        val merchant = intent.getStringExtra(merchantIdKey).toString()
        val username = intent.getStringExtra(usernameIdKey).toString()

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

}