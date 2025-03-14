package com.sharanya.mmm

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.Button
import android.widget.ImageButton

class loginscreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_loginscreen)
        val backButton = findViewById<ImageButton>(R.id.backtologinbtn)
        backButton.setOnClickListener {
            val intent = Intent(this, login::class.java)
            startActivity(intent)
        }
        val loginButton = findViewById<Button>(R.id.btnlogin)
        loginButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }


}