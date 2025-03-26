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

class loginscreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_loginscreen)

        val backButton = findViewById<ImageButton>(R.id.backtologinbtn)
        val loginButton = findViewById<Button>(R.id.btnlogin)
        val emailEditText = findViewById<EditText>(R.id.etName)
        val passwordEditText = findViewById<AppCompatEditText>(R.id.edtPass)


        backButton.setOnClickListener {
            val intent = Intent(this, login::class.java)
            startActivity(intent)
        }

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email == "admin" && password == "1234") {
                // Admin Login
                Toast.makeText(this, "Welcome, Admin!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, AdminMainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                // Regular User Login
                Toast.makeText(this, "Welcome, User!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}
