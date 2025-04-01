package com.sharanya.mmm

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class signupscreen : AppCompatActivity() {
    private lateinit var edtName: EditText
    private lateinit var etMail: EditText
    private lateinit var edtPass: EditText
    private lateinit var btnStart: Button
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signupscreen)

        edtName = findViewById(R.id.etName)
        etMail = findViewById(R.id.etEmail)
        edtPass = findViewById(R.id.edtPass)
        btnStart = findViewById(R.id.btnStart)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)  // Use the class-level sharedPreferences

        btnStart.setOnClickListener {
            registerUser()
        }

        val backButton = findViewById<ImageButton>(R.id.backtologinbtn)
        backButton.setOnClickListener {
            val intent = Intent(this, login::class.java)
            startActivity(intent)
        }
    }

    private fun registerUser() {
        val name = edtName.text.toString().trim()
        val email = etMail.text.toString().trim()
        val password = edtPass.text.toString().trim()

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val user = users(name, email, password)  // Keep your existing users class

        RetrofitClient.instance.registerUser(user).enqueue(object : Callback<users> {
            override fun onResponse(call: Call<users>, response: Response<users>) {
                if (response.isSuccessful && response.body() != null) {
                    val registeredUser = response.body()!!

                    val userManager = UserManager(this@signupscreen)
                    userManager.saveUser(registeredUser)  // Save user session

                    Toast.makeText(this@signupscreen, "Registration successful!", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this@signupscreen, loginscreen::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@signupscreen, "Registration failed!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<users>, t: Throwable) {
                Log.e("API_ERROR", "Error:", t)
                Toast.makeText(this@signupscreen, "Network Error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }



//    private fun saveUserData(userId: Int, name: String, email: String) {
//        val editor = sharedPreferences.edit()
//        editor.putInt("USER_ID", userId)  // Save user ID
//        editor.putString("USERNAME", name)  // Save user name
//        editor.putString("EMAIL", email)  // Save user email
//        editor.apply()  // Commit the changes
//    }

    class UserManager(context: Context) {
        private val sharedPreferences: SharedPreferences = context.getSharedPreferences("UserSession", Context.MODE_PRIVATE)

        fun saveUser(user: users) {
            val editor = sharedPreferences.edit()
            editor.putString("name", user.name)
            editor.putString("email", user.email)
            editor.putString("password", user.password) // Store if needed
            editor.apply()
        }

        fun getUser(): users? {
            val name = sharedPreferences.getString("name", null)
            val email = sharedPreferences.getString("email", null)
            val password = sharedPreferences.getString("password", null)

            return if (name != null && email != null && password != null) {
                users(name = name, email = email, password = password)
            } else {
                null
            }
        }

        fun clearUser() {
            sharedPreferences.edit().clear().apply()
        }
    }


}
