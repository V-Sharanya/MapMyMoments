package com.sharanya.mmm

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateFragment : Fragment() {

    private lateinit var startDestination: EditText
    private lateinit var endDestination: EditText
    private lateinit var addDestinationButton: FloatingActionButton
    private lateinit var dateField: EditText
    private lateinit var descriptionField: EditText
    private lateinit var selectImageButton: Button
    private lateinit var imagePreview: ImageView
    private lateinit var createPostButton: Button

    private var selectedImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_create, container, false)

        // Initialize UI elements
        startDestination = view.findViewById(R.id.startDestination)
        endDestination = view.findViewById(R.id.endDestination)
        //addDestinationButton = view.findViewById(R.id.addDestinationButton)
       // dateField = view.findViewById(R.id.date)
        descriptionField = view.findViewById(R.id.description)
        selectImageButton = view.findViewById(R.id.selectImage)
        imagePreview = view.findViewById(R.id.imagePreview)
        createPostButton = view.findViewById(R.id.create)

        // Initially hide endDestination
        endDestination.visibility = View.GONE

        // Add destination button: Show end destination when clicked
//        addDestinationButton.setOnClickListener {
//            endDestination.visibility = View.VISIBLE
//        }

        // Select image button click event
        selectImageButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 100)
        }

        // Create post button click event
        createPostButton.setOnClickListener {
            savePostToDatabase()
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageUri = data.data
            imagePreview.setImageURI(selectedImageUri)
        }
    }

    private fun savePostToDatabase() {
        val startDest = startDestination.text.toString().trim()
        val date = dateField.text.toString().trim()
        val description = descriptionField.text.toString().trim()
        val imageUrl = selectedImageUri?.toString() ?: ""  // Optional
        val endDest = if (endDestination.visibility == View.VISIBLE) endDestination.text.toString().trim() else ""

        // Validate required fields
        if (startDest.isEmpty() || date.isEmpty() || description.isEmpty()) {
            Toast.makeText(requireContext(), "Start destination, date, and description are required!", Toast.LENGTH_SHORT).show()
            return
        }

        val post = posts(startDest, date, description, imageUrl, endDest)

        RetrofitClient.instance.savePost(post).enqueue(object : Callback<posts> {
            override fun onResponse(call: Call<posts>, response: Response<posts>) {
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Post Created Successfully!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Failed to create post!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<posts>, t: Throwable) {
                Toast.makeText(requireContext(), "Network Error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}
