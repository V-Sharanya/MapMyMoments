package com.sharanya.mmm

import android.app.Dialog
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment() {

    private lateinit var profileImage: ImageView
    private lateinit var cameraIcon: ImageView
    private lateinit var editProfileButton: Button
    private lateinit var setAvatar: TextView
    private lateinit var tvname:TextView
    private lateinit var tvemail:TextView


    private lateinit var pickImageLauncher: ActivityResultLauncher<Intent>

    private val avatarList = arrayOf(
        R.drawable.avatar1, R.drawable.avatar2, R.drawable.avatar3,
        R.drawable.avatar4, R.drawable.avatar5, R.drawable.avatar6
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view= inflater.inflate(R.layout.fragment_profile, container, false)
        tvname=view.findViewById(R.id.userName)
        tvemail=view.findViewById(R.id.usermail)
        val userid=getUserId()
        tvname.text=getUserName()
        tvemail.text=getUserEmail()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize Views
        profileImage = view.findViewById(R.id.profileImage)
        cameraIcon = view.findViewById(R.id.cameraIcon)
        setAvatar = view.findViewById(R.id.setAvatar)
        editProfileButton = view.findViewById(R.id.editProfileButton)  // Initialize editProfileButton

        // Set Click Listener
        editProfileButton.setOnClickListener {
            val intent = Intent(requireContext(), EditProfileDetails::class.java)
            startActivity(intent)
        }

        // Image Picker for Camera Icon
        pickImageLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val imageUri: Uri? = data?.data
                profileImage.setImageURI(imageUri)
            }
        }

        cameraIcon.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            pickImageLauncher.launch(intent)
        }

        setAvatar.setOnClickListener {
            showAvatarDialog()
        }

    }


    private fun getUserId():Int{
        val sharedPreferences = requireContext().getSharedPreferences("UserPrefs", AppCompatActivity.MODE_PRIVATE)
        val userId = sharedPreferences.getString("USER_ID", null)
        return userId?.toIntOrNull() ?: -1

    }
    private fun getUserName():String{
        val sharedPreferences = requireContext().getSharedPreferences("UserPrefs", AppCompatActivity.MODE_PRIVATE)
        val userName = sharedPreferences.getString("USER_NAME", null)
        return userName!!
    }
    private fun getUserEmail():String{
        val sharedPreferences = requireContext().getSharedPreferences("UserPrefs", AppCompatActivity.MODE_PRIVATE)
        val userEmail = sharedPreferences.getString("USER_EMAIL", null)
        return userEmail!!
    }

    private fun showAvatarDialog() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_avatar_selection)

        val avatarGrid: GridView = dialog.findViewById(R.id.avatarGridView)
        avatarGrid.adapter = AvatarAdapter(requireContext(), avatarList)

        avatarGrid.setOnItemClickListener { _, _, position, _ ->
            profileImage.setImageResource(avatarList[position])
            dialog.dismiss()
        }

        dialog.show()
    }
}
