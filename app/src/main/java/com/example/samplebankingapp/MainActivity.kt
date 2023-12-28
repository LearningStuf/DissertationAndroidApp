package com.example.samplebankingapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.example.frauddetectionlibrary.FraudCheckUser


class MainActivity : AppCompatActivity() {
    lateinit var usernameInput: EditText
    lateinit var loginButton: Button
    lateinit var textRegister: TextView
    val usernameKey = "username"
    val checkUserResponse = "check_user"

    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPreferences = getSharedPreferences("LoginSessions", Context.MODE_PRIVATE)
        usernameInput = findViewById(R.id.username_input)
        loginButton = findViewById(R.id.login_btn)
        textRegister = findViewById(R.id.register_text)


        loginButton.setOnClickListener {
            val checkUserIntent = Intent(this@MainActivity, FraudCheckUser::class.java)
            checkUserIntent.putExtra(usernameKey, usernameInput.text.toString())
            checkUserActivityResultLauncher.launch(checkUserIntent)

        }

        textRegister.setOnClickListener {
            Log.i("Click text", "Text has been clicked")
            val intent = Intent(this@MainActivity, RegistrationActivity::class.java)
            startActivity(intent)
        }
    }


    private val checkUserActivityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data = result.data
            if (result.resultCode == Activity.RESULT_OK) {

                showAlert("Success", "User has been verified")


            } else {
                showAlert("Failed", data?.getStringExtra(checkUserResponse).toString())
            }
        }


    private fun showAlert(title: String, message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                if(title.equals("Success")){
                    val intent = Intent(this@MainActivity, PaymentsActivity::class.java)
                    startActivity(intent)
                }
                dialog.dismiss()
            }
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()

    }

}