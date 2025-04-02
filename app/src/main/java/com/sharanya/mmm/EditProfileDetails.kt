package com.sharanya.mmm

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback

class EditProfileDetails : AppCompatActivity() {
    private lateinit var editAge: EditText
    private lateinit var editBio: EditText
    private lateinit var genderSpinner: Spinner
    private lateinit var saveProfileButton: Button
    private var selectedGender: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile_details)

        editAge = findViewById(R.id.editAge)
        editBio = findViewById(R.id.editBio)
        genderSpinner = findViewById(R.id.genderSpinner)
        saveProfileButton = findViewById(R.id.saveProfileButton)

//var profileAvatar = intent.getIntExtra("profileAvatar", -1)

        val userid = getUserId()

        // Set up gender spinner
        val adapter = ArrayAdapter.createFromResource(
            this, R.array.gender_options, android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        genderSpinner.adapter = adapter

        genderSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedGender = parent.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        saveProfileButton.setOnClickListener {

            val age = editAge.text.toString()
            val bio = editBio.text.toString()
            val gender = selectedGender
            updateprofile(userid,bio,age,gender)


        }

    }
    private fun getUserId():Int{
        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val userId = sharedPreferences.getString("USER_ID", null)
        return userId?.toIntOrNull() ?: -1

    }

    private fun updateprofile(id:Int,bio:String,age:String,gender:String){
        val details= mapOf(
            "bio" to bio,
            "age" to age,
            "gender" to gender,
            "profileimage" to ""
        )
        val apiservice=RetrofitClient.instance
        val call=apiservice.updateprofile(id,details)
        call.enqueue(object : Callback<Unit?> {
            override fun onResponse(call: Call<Unit?>, response: retrofit2.Response<Unit?>) {
                if(response.isSuccessful){
                    Toast.makeText(this@EditProfileDetails,"profile updated",Toast.LENGTH_SHORT).show()
                    val fragment = ProfileFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragprofpage, fragment) // Make sure `fragment_container` is the ID of your container
                        .addToBackStack(null)
                        .commit()
                }
                else{
                    Log.d("main acitivty","failue ")
                }
            }

            override fun onFailure(call: Call<Unit?>, t: Throwable) {
                Log.d("main acitivty","failue in "+t.message)
            }
        })
    }




}