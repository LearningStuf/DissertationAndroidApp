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
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.example.frauddetectionlibrary.FraudCheckUser
import com.example.frauddetectionlibrary.FraudLoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    lateinit var usernameInput  : EditText
    lateinit var passwordInput  : EditText
    lateinit var loginButton    : Button
    lateinit var textRegister   : TextView
    val usernameKey = "username"
    val checkUserResponse = "check_user"

    private lateinit var sharedPreferences: SharedPreferences
    val retrofitBuilder = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("http://10.0.2.2:8000/")
        .build()
        .create(ApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPreferences = getSharedPreferences("LoginSessions", Context.MODE_PRIVATE)
        usernameInput = findViewById(R.id.username_input)
        loginButton = findViewById(R.id.login_btn)
        textRegister = findViewById(R.id.register_text)


        loginButton.setOnClickListener{
            var username = usernameInput.text.toString()
            var password = passwordInput.text.toString()
            Log.i("Test credentials", "username: $username and Password $password")
//            This is where we are adding the library code
//            val intent = Intent(this@MainActivity, FraudLoginActivity::class.java)
//            startActivity(intent)

            val checkUserIntent = Intent(this@MainActivity, FraudCheckUser::class.java)
            checkUserIntent.putExtra(usernameKey,usernameInput.text.toString() )
            checkUserActivityResultLauncher.launch(checkUserIntent)

//            performLoginUser()
        }

        textRegister.setOnClickListener {
            Log.i("Click text", "Text has been clicked")
            val intent = Intent(this@MainActivity, RegistrationActivity::class.java)
//            intent.putExtra(usernameKey,usernameInput.text.toString() )
            startActivity(intent)
        }
    }

    // This is the activity listener
//    val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
//
//        if (result.resultCode == Activity.RESULT_OK && result.data?.getStringExtra(checkUserResponse)
//                .equals("Successful")) {
//
//            val intent = result.data
//            val response = intent?.getStringExtra(checkUserResponse).toString()
//            showAlert("Important",response)
//        }
//    }

    private val checkUserActivityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data = result.data
            if (result.resultCode == Activity.RESULT_OK) {

                showAlert("Success", "User has been verified")


            }
            else {
                showAlert("Failed", data?.getStringExtra(checkUserResponse).toString())
            }
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
    private fun performLoginUser() {

        val requestBody = LoginBody(
            usernameInput.text.toString(),
            passwordInput.text.toString()
        )
        val retrofitData = retrofitBuilder.loginUser(requestBody)
        retrofitData.enqueue(object : Callback<LoginResponse?> {
            override fun onResponse(
                call: Call<LoginResponse?>,
                response: Response<LoginResponse?>
            ) {
                if(response.code().equals(200)){
                    val editor = sharedPreferences.edit()
                    editor.putString(usernameInput.text.toString(), response.body()?.token) // Replace "key" and "value" with your data
                    editor.apply()
                    Log.i(usernameInput.text.toString(), "${response.body()?.token}")
                    val intent = Intent(this@MainActivity, PaymentsActivity::class.java)
                    startActivity(intent)

                }

                else{
                    showAlert("Error", "Incorrect credentials")
                }
            }

            override fun onFailure(call: Call<LoginResponse?>, t: Throwable) {
                Log.i("Login Failure", "Fail ${t.message.toString()}")
            }
        })

    }
}