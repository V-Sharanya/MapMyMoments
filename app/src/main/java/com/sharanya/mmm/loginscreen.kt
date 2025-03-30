package com.sharanya.mmm

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import com.sharanya.mmm.model.LoginRequest
import com.sharanya.mmm.model.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Log

class loginscreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_loginscreen)

        val backButton = findViewById<ImageButton>(R.id.backtologinbtn)
        val loginButton = findViewById<Button>(R.id.btnlogin)
        val emailEditText = findViewById<AppCompatEditText>(R.id.etName)
        val passwordEditText = findViewById<AppCompatEditText>(R.id.edtPass)

        backButton.setOnClickListener {
            val intent = Intent(this, login::class.java)
            startActivity(intent)
        }

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            loginUser(email, password)
            Log.d("LoginDebug", "Attempting login with email: $email")  // Debug log
            loginUser(email, password)
        }
    }
    private fun loginUser(email: String, password: String) {
        val request = LoginRequest(email, password) // Ensure LoginRequest has 'email'

        RetrofitClient.instance.loginUser(request).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()

                    if (loginResponse != null && loginResponse.success) {
                        Toast.makeText(this@loginscreen, loginResponse.message, Toast.LENGTH_SHORT).show()

                        val intent = if (loginResponse.role == "admin") {
                            Intent(this@loginscreen, AdminMainActivity::class.java)
                        } else {
                            Intent(this@loginscreen, MainActivity::class.java)
                        }
                        startActivity(intent)
                        finish()
                    } else {
                        // Show a message for incorrect email/password
                        Toast.makeText(this@loginscreen, "Incorrect email or password!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    if (response.code() == 401) {
                        Toast.makeText(this@loginscreen, "Unauthorized: Incorrect email or password!", Toast.LENGTH_SHORT).show()
                    } else {
                        val errorBody = response.errorBody()?.string() ?: "Unknown error"
                        Toast.makeText(this@loginscreen, "Login failed: $errorBody", Toast.LENGTH_LONG).show()
                    }
                }
            }


            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                // Handle network error
                Toast.makeText(this@loginscreen, "Network error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

}
