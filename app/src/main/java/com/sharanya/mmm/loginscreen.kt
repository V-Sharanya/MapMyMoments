package com.sharanya.mmm

import android.content.Intent
import android.os.Bundle
import android.widget.Button
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
    private lateinit var emailEditText: AppCompatEditText
    private lateinit var passwordEditText: AppCompatEditText
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_loginscreen)

        val backButton = findViewById<ImageButton>(R.id.backtologinbtn)
        loginButton = findViewById(R.id.btnlogin)
        emailEditText = findViewById(R.id.etName)
        passwordEditText = findViewById(R.id.edtPass)

        backButton.setOnClickListener {
            val intent = Intent(this, login::class.java)
            startActivity(intent)
        }

        loginButton.setOnClickListener {
            validateAndLogin()
        }
    }

    private fun validateAndLogin() {
        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()

        var isValid = true  // Track validation status

        // Reset previous errors
        emailEditText.error = null
        passwordEditText.error = null

        // Validate Email
        if (email.isEmpty()) {
            emailEditText.error = "Email is required"
            emailEditText.setBackgroundResource(R.drawable.edittext_error)
            isValid = false
        } else {
            emailEditText.setBackgroundResource(R.drawable.edittext_normal)
        }

        // Validate Password
        if (password.isEmpty()) {
            passwordEditText.error = "Password is required"
            passwordEditText.setBackgroundResource(R.drawable.edittext_error)
            isValid = false
        } else {
            passwordEditText.setBackgroundResource(R.drawable.edittext_normal)
        }

        // Stop login if validation fails
        if (!isValid) return

        loginUser(email, password)
        Log.d("LoginDebug", "Attempting login with email: $email")
    }

    private fun loginUser(email: String, password: String) {
        val request = LoginRequest(email, password)

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
                Toast.makeText(this@loginscreen, "Network error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}
