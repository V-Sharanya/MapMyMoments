package com.sharanya.mmm

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.HapticFeedbackConstants
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class signupscreen : AppCompatActivity() {
    private lateinit var edtName: EditText
    private lateinit var etMail: EditText
    private lateinit var edtPass: EditText
    private lateinit var btnStart: Button
    private lateinit var edtConfirmPass: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signupscreen)

        edtName = findViewById(R.id.etName)
        etMail = findViewById(R.id.etEmail)
        edtPass = findViewById(R.id.edtPass)
        btnStart = findViewById(R.id.btnStart)
        edtConfirmPass = findViewById(R.id.edtConfirmPass)

        btnStart.setOnClickListener {
            it.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
            Log.d("HAPTIC_FEEDBACK", "Button haptic triggered")
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
        val confirmPass = edtConfirmPass.text.toString().trim()

        var isValid = true  // Track validation status

        // Reset previous errors
        edtName.error = null
        etMail.error = null
        edtPass.error = null
        edtConfirmPass.error = null

        // Validate Name
        if (name.isEmpty()) {
            edtName.error = "Name is required"
            edtName.setBackgroundResource(R.drawable.edittext_error) // Set red border
            isValid = false
        } else {
            edtName.setBackgroundResource(R.drawable.edittext_normal) // Reset border
        }

        // Validate Email
        if (email.isEmpty()) {
            etMail.error = "Email is required"
            etMail.setBackgroundResource(R.drawable.edittext_error)
            isValid = false
        } else {
            etMail.setBackgroundResource(R.drawable.edittext_normal)
        }

        // Validate Password
        if (password.isEmpty()) {
            edtPass.error = "Password is required"
            edtPass.setBackgroundResource(R.drawable.edittext_error)
            isValid = false
        } else {
            edtPass.setBackgroundResource(R.drawable.edittext_normal)
        }

        // Validate Confirm Password
        if (confirmPass.isEmpty()) {
            edtConfirmPass.error = "Confirm Password is required"
            edtConfirmPass.setBackgroundResource(R.drawable.edittext_error)
            isValid = false
        } else {
            edtConfirmPass.setBackgroundResource(R.drawable.edittext_normal)
        }

        // Check if passwords match
        if (password != confirmPass) {
            edtConfirmPass.error = "Passwords do not match"
            edtConfirmPass.setBackgroundResource(R.drawable.edittext_error)
            isValid = false
        }

        // Stop registration if validation fails
        if (!isValid) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                btnStart.performHapticFeedback(HapticFeedbackConstants.REJECT)
            } else {
                btnStart.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS) // fallback
            }
            return
        }



        val user = users(name, email, password)


        RetrofitClient.instance.registerUser(user).enqueue(object : Callback<responseUser?> {
            override fun onResponse(call: Call<responseUser?>, response: Response<responseUser?>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@signupscreen, "Registration successful!", Toast.LENGTH_SHORT).show()
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        btnStart.performHapticFeedback(HapticFeedbackConstants.CONFIRM)
                    } else {
                        btnStart.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                    }


                    // Clear input fields
                    edtName.text.clear()
                    etMail.text.clear()
                    edtPass.text.clear()
                    edtConfirmPass.text.clear()
                    println("this is"+response.body()!!.name)
                    createprofile(response.body()!!.id,response.body()!!.name,response.body()!!.email)

                    // Navigate to login screen
                    val intent = Intent(this@signupscreen, loginscreen::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@signupscreen, "Registration failed!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<responseUser?>, t: Throwable) {
                Log.e("API_ERROR", "Error:", t)
                Toast.makeText(this@signupscreen, "Network Error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
       /* RetrofitClient.instance.registerUser(user).enqueue(object : lback<responseUser> {
            override fun onResponse(call: Call<responseUser>, response: Response<responseUser>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@signupscreen, "Registration successful!", Toast.LENGTH_SHORT).show()

                    // Clear input fields
                    edtName.text.clear()
                    etMail.text.clear()
                    edtPass.text.clear()
                    edtConfirmPass.text.clear()
                    println("this is"+response.body()!!.id)
                    createprofile(response.body()!!.id,response.body()!!.name,response.body()!!.email)


                    // Navigate to login screen
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
    }*/


}
    private fun createprofile(id:Int,name:String,email:String){
        val profileuser=ProfileUser(id,name,email)
        val apiservice=RetrofitClient.instance
        val call=apiservice.crateprofile(profileuser)
        call.enqueue(object : Callback<Unit?> {
            override fun onResponse(call: Call<Unit?>, response: Response<Unit?>) {

            }
            override fun onFailure(call: Call<Unit?>, t: Throwable) {
                Log.d("main actvity","failure in "+t.message)
            }
        })
    }
}
