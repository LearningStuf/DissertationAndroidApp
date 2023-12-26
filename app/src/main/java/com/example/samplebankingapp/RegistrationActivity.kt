package com.example.samplebankingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegistrationActivity : AppCompatActivity() {

    lateinit var usernameInput: EditText
    lateinit var passwordInput: EditText
    lateinit var confirmPasswordInput: EditText
    lateinit var dateOfBirthInput: EditText
    lateinit var genderInput: EditText
    lateinit var registerButton: Button

    val retrofitBuilder = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("http://10.0.2.2:8000/")
        .build()
        .create(ApiService::class.java)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        usernameInput = findViewById(R.id.username_input)
        passwordInput = findViewById(R.id.password_input)
        confirmPasswordInput = findViewById(R.id.password_repeat_input)
        dateOfBirthInput = findViewById(R.id.date_of_birth)
        genderInput = findViewById(R.id.gender)
        registerButton = findViewById(R.id.register_button)

        registerButton.setOnClickListener {
            var username = usernameInput.text.toString()
            var password = passwordInput.text.toString()
            var confirmPassword = confirmPasswordInput.text.toString()
            var dateOfBirth = dateOfBirthInput.text.toString()
            var gender = genderInput.toString().uppercase()


            registerButton.setOnClickListener {
                Log.i(
                    "Value of the passwords ",
                    "First password $password Second password $confirmPassword"
                )

                if (password.equals(confirmPassword)) {
                    performRegisterUser()

                    Log.i("Password Match", "Password you enterd matches")
                } else {
                    showAlert(
                        "Password doesn't match",
                        "The password you entered does not match"
                    )
                }

            }

        }


    }


    private fun showAlert(title: String, message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                // Continue with registration or handle as needed
                dialog.dismiss()
            }
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
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
                // PIN input is not empty, perform actions with the PIN
                // For example, save the PIN to preferences or database
            } else {
                // PIN input is empty, handle error or inform the user
            }
            dialog.dismiss()
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            // Handle cancel action if needed
            dialog.dismiss()
        }

        builder.setIcon(android.R.drawable.ic_dialog_info)
        builder.show()
    }

    private fun performHealthCheck() {

        val retrofitData = retrofitBuilder.healthCheck()
        retrofitData.enqueue(object : Callback<HealthResponse?> {
            override fun onResponse(
                call: Call<HealthResponse?>,
                response: Response<HealthResponse?>
            ) {
                val responseBody = response.body()
                if (responseBody != null) {
                    Log.i(
                        "health Check",
                        "Value of health check ${responseBody.status}"
                    )
                }

            }

            override fun onFailure(call: Call<HealthResponse?>, t: Throwable) {
                Log.i(
                    "health Check failure",
                    "Value of health check ${t.message}"
                )
            }
        })



    }

    private fun performRegisterUser() {

        val requestBody = RegisterBody(
            usernameInput.text.toString(),
            passwordInput.text.toString(),
            dateOfBirthInput.text.toString(),
            genderInput.text.toString()
        )
        val retrofitData = retrofitBuilder.registerUser(requestBody)
        retrofitData.enqueue(object : Callback<RegisterResponse?> {
            override fun onResponse(
                call: Call<RegisterResponse?>,
                response: Response<RegisterResponse?>
            ) {
                val responseBody = response.body()
                if (responseBody != null) {
                    Log.i(
                        "RegisterCheck",
                        "Value of health check ${responseBody.message}"
                    )
                }

            }


            override fun onFailure(call: Call<RegisterResponse?>, t: Throwable) {
                Log.i(
                    "Register Failure",
                    "Registration ${t.message.toString()}"
                )
            }
        })

    }
}