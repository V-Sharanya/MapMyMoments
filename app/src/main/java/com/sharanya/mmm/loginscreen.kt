package com.sharanya.mmm

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_loginscreen)

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)

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

        var isValid = true

        emailEditText.error = null
        passwordEditText.error = null

        if (email.isEmpty()) {
            emailEditText.error = "Email is required"
            isValid = false
        }

        if (password.isEmpty()) {
            passwordEditText.error = "Password is required"
            isValid = false
        }

        if (!isValid) return

        loginUser(email, password)
    }

    private fun loginUser(email: String, password: String) {
        val request = LoginRequest(email, password)

        RetrofitClient.instance.loginUser(request).enqueue(object : Callback<responseUser> {
            override fun onResponse(call: Call<responseUser>, response: Response<responseUser>) {
                if (response.isSuccessful) {
                        val loginResponse = response.body()
                        saveUserDetails(loginResponse!!.id,loginResponse.email,loginResponse.role,loginResponse.name,loginResponse.password)
                        Toast.makeText(this@loginscreen,"Login Successful", Toast.LENGTH_SHORT).show()
                        val intent = if (loginResponse.role == "admin") {
                            Intent(this@loginscreen, AdminMainActivity::class.java)
                        } else {
                            Intent(this@loginscreen, MainActivity::class.java)
                        }
                        startActivity(intent)
                        finish()
                } else {
                    if (response.code() == 401) {
                        Toast.makeText(this@loginscreen, "Unauthorized: Incorrect email or password!", Toast.LENGTH_SHORT).show()
                    } else {
                        val errorBody = response.errorBody()?.string() ?: "Unknown error"
                        Toast.makeText(this@loginscreen, "Login failed", Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(call: Call<responseUser>, t: Throwable) {
                Toast.makeText(this@loginscreen, "Network error: ${t.message}", Toast.LENGTH_LONG).show()
            }

        })
    }
    private fun saveUserDetails(id:Int ,email: String, role: String,name:String,password: String) {
        val editor = sharedPreferences.edit()
        editor.putString("USER_ID",id.toString())
        editor.putString("USER_EMAIL", email)
        editor.putString("USER_NAME", name)
        editor.putString("USER_ROLE", role)
        editor.putBoolean("IS_LOGGED_IN", true)
        editor.putString("USER_PASSWORD", password)
        editor.apply()
    }
}
