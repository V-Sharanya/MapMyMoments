package com.sharanya.mmm

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class signupscreen : AppCompatActivity() {
    private lateinit var edtName: EditText
    private lateinit var etmail: EditText
    private lateinit var edtpass: EditText
    private lateinit var btnStart: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signupscreen)

        edtName = findViewById(R.id.etName)
        etmail = findViewById(R.id.etEmail)
        edtpass = findViewById(R.id.edtPass)
        btnStart = findViewById(R.id.btnStart)

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
        val email = etmail.text.toString().trim()
        val password = edtpass.text.toString().trim()

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val user = users(name, email, password)
        RetrofitClient.instance.registerUser(user).enqueue(object : Callback<users> {
            override fun onResponse(call: Call<users>, response: Response<users>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@signupscreen, "Registration successful!", Toast.LENGTH_SHORT).show()
                    edtName.text.clear()
                    etmail.text.clear()
                    edtpass.text.clear()
                } else {
                    Toast.makeText(this@signupscreen, "Registration failed!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<users>, t: Throwable) {
                Log.e("API_ERROR", "Error: ${t.message}")
                Toast.makeText(this@signupscreen, "Network Error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}