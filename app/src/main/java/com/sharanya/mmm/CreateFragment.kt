package com.sharanya.mmm

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CreateFragment : Fragment() {

    private lateinit var endDestination: EditText
    private lateinit var addDestinationButton: FloatingActionButton
    private lateinit var selectImage: Button
    private lateinit var imagePreview: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_create, container, false)

        // Initialize UI elements
        endDestination = view.findViewById(R.id.endDestination)
        addDestinationButton = view.findViewById(R.id.addDestinationButton)
        selectImage = view.findViewById(R.id.selectImage)
        imagePreview = view.findViewById(R.id.imagePreview)

        // Set click listener for adding destination
        addDestinationButton.setOnClickListener {
            endDestination.visibility = View.VISIBLE
        }

        // Set click listener for selecting image
        selectImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 100)
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == Activity.RESULT_OK && data != null) {
            val imageUri: Uri? = data.data
            imagePreview.setImageURI(imageUri)
        }
    }
}
