package com.sharanya.mmm

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class EditProfileActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var usernameEditText: EditText
    private lateinit var ageEditText: EditText
    private lateinit var genderSpinner: Spinner
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        nameEditText = findViewById(R.id.editTextName)
        usernameEditText = findViewById(R.id.editTextUsername)
        ageEditText = findViewById(R.id.editTextAge)
        genderSpinner = findViewById(R.id.spinnerGender)
        saveButton = findViewById(R.id.buttonSave)

        val genderOptions = arrayOf("Male", "Female")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, genderOptions)
        genderSpinner.adapter = adapter

        // Load existing data (Optional)
        nameEditText.setText("Alekhya")
        usernameEditText.setText("alekhya33@gmail.com")
        ageEditText.setText("20")
        genderSpinner.setSelection(1)

        saveButton.setOnClickListener {
            // Save logic (e.g., SharedPreferences or Database)
            finish()
        }
    }
}
