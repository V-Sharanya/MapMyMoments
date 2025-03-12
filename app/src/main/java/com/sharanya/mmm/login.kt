package com.sharanya.mmm

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.Button

class login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        val loginButton = findViewById<Button>(R.id.logbtn)
        val signupButton = findViewById<Button>(R.id.signupbtn)
        loginButton.setOnClickListener {
            val intent = Intent(this, loginscreen::class.java)
            startActivity(intent)
        }
        signupButton.setOnClickListener {
            val intent = Intent(this , signupscreen::class.java)
            startActivity(intent)
        }
    }
}