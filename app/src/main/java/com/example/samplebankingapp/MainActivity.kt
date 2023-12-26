package com.example.samplebankingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    lateinit var usernameInput  : EditText
    lateinit var passwordInput  : EditText
    lateinit var loginButton    : Button
    lateinit var textRegister   : TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        usernameInput = findViewById(R.id.username_input)
        passwordInput = findViewById(R.id.password_input)
        loginButton = findViewById(R.id.login_btn)
        textRegister = findViewById(R.id.register_text)


        loginButton.setOnClickListener{
            var username = usernameInput.text.toString()
            var password = passwordInput.text.toString()
            Log.i("Test credentials", "username: $username and Password $password")
        }

        textRegister.setOnClickListener {
            Log.i("Click text", "Text has been clicked")
            val intent = Intent(this@MainActivity, RegistrationActivity::class.java)
            startActivity(intent)
        }
    }
}