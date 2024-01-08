package com.example.samplebankingapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import com.example.frauddetectionlibrary.FraudCheckUser
import com.example.frauddetectionlibrary.FraudPaymentActivity

class PaymentsActivity : AppCompatActivity() {


    val spinnerData = listOf("transportation", "health", "otherservices", "food", "hotelservices", "barsandrestaurants", "tech", "sportsandtoys","wellnessandbeauty","hyper","fashion","home","contents","travel","leisure")
    lateinit var paymentButton: Button
    lateinit var amount: EditText
    lateinit var selectedItem: String
    lateinit var merchant : EditText
    val amountKey = "amount"
    val categoryKey = "category"
    val merchantIdKey = "merchant"
    val apiResponseKey = "api_response"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payments)

        paymentButton = findViewById(R.id.payment_button)
        amount = findViewById(R.id.amount_input)
        merchant = findViewById(R.id.merchant_id)

        val spinner: Spinner = findViewById(R.id.category_input)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerData)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Handle the selected item here
                selectedItem = spinnerData[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle when no item is selected
            }
        }

        paymentButton.setOnClickListener {
            val checkUserIntent = Intent(this@PaymentsActivity, FraudPaymentActivity::class.java)
            checkUserIntent.putExtra(amountKey, amount.text.toString())
            checkUserIntent.putExtra(categoryKey, selectedItem)
            checkUserIntent.putExtra(merchantIdKey, merchant.text.toString())

            PaymentActivityResultLauncher.launch(checkUserIntent)

        }




    }

    private val PaymentActivityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data = result.data
            if (result.resultCode == Activity.RESULT_OK) {

//                showAlert("Success", "User has been verified")


            } else {
//                showAlert("Failed", data?.getStringExtra(checkUserResponse).toString())
            }
        }
}