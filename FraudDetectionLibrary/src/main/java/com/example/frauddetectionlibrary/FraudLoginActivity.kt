package com.example.frauddetectionlibrary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class FraudLoginActivity : AppCompatActivity() {


    lateinit var usernameInput  : EditText
    lateinit var passwordInput  : EditText
    lateinit var loginButton    : Button
    lateinit var textRegister   : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fraud_login)

        usernameInput = findViewById(R.id.username_input)
        passwordInput = findViewById(R.id.password_input)
        loginButton = findViewById(R.id.login_btn)
        textRegister = findViewById(R.id.register_text)

        loginButton.setOnClickListener{
//            var username = usernameInput.text.toString()
            val username = "Hello this is from the second activity"
            val intent = Intent()
            intent.putExtra("api_response", username)
            setResult(RESULT_OK,intent)
            finish()
        }


    }
}