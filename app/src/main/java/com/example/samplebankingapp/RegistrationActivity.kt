package com.example.samplebankingapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.example.frauddetectionlibrary.FraudRegisterActivity


class RegistrationActivity : AppCompatActivity() {

    lateinit var usernameInput: EditText
    lateinit var passwordInput: EditText
    lateinit var confirmPasswordInput: EditText
    lateinit var dateOfBirthInput: EditText
    lateinit var genderInput: EditText
    lateinit var registerButton: Button

    val usernameKey = "username"
    val passwordKey = "password"
    val dateOfBirthKey = "dob"
    val genderKey = "gender"
    val pinKey = "pin"


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
//                if(username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || dateOfBirth.isEmpty() || gender.isEmpty()){
//                    showAlert("Error", "Need to enter all the fields")
//                }
                 if (password.equals(confirmPassword)) {
                    showSetPinDialog()
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
                if(title.equals("Success Registration")){
                    val intent = Intent(this@RegistrationActivity, MainActivity::class.java)
                    startActivity(intent)
                }
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

                val registerUserIntent = Intent(this@RegistrationActivity, FraudRegisterActivity::class.java)
                registerUserIntent.putExtra(usernameKey,usernameInput.text.toString())
                registerUserIntent.putExtra(passwordKey,passwordInput.text.toString())
                registerUserIntent.putExtra(dateOfBirthKey,dateOfBirthInput.text.toString())
                registerUserIntent.putExtra(genderKey,genderInput.text.toString())
                registerUserIntent.putExtra(pinKey,enteredPin)

                fraudRegisterActivityResultLauncher.launch(registerUserIntent)


            } else {
                showAlert("Error", "Cannot Enter an empty pin")
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


    private val fraudRegisterActivityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data = result.data
            if (result.resultCode == Activity.RESULT_OK) {
                showAlert("Success Registration", "User has been registered")
            }
            else {
                showAlert("Failed", "User registration failed")
            }
        }





}