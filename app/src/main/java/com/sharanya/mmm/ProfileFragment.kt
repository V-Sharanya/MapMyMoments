package com.sharanya.mmm

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {

    private lateinit var profileImage: ImageView
    private lateinit var cameraIcon: ImageView
    private lateinit var editProfileButton: Button
    private lateinit var setAvatar: TextView
    private lateinit var tvName: TextView
    private lateinit var tvUsername: TextView
    private lateinit var tvBio: TextView
    private lateinit var tvGender: TextView

    private lateinit var pickImageLauncher: ActivityResultLauncher<Intent>

    private val avatarList = arrayOf(
        R.drawable.avatar1, R.drawable.avatar2, R.drawable.avatar3,
        R.drawable.avatar4, R.drawable.avatar5, R.drawable.avatar6
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileImage = view.findViewById(R.id.profileImage)
        cameraIcon = view.findViewById(R.id.cameraIcon)
        editProfileButton = view.findViewById(R.id.editProfileButton)
        setAvatar = view.findViewById(R.id.setAvatar)
        tvName = view.findViewById(R.id.tvName)
        tvUsername = view.findViewById(R.id.tvUsername)
        tvBio = view.findViewById(R.id.tvBio)
        tvGender = view.findViewById(R.id.tvGender)

        // Fetch user data when the fragment is created
        fetchUserData()

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

        editProfileButton.setOnClickListener {
            startActivity(Intent(requireContext(), EditProfileActivity::class.java))
        }
    }

    // Method to fetch user profile data
    private fun fetchUserData() {
        RetrofitClient.instance.getUsers().enqueue(object : Callback<ResponseWrapper> {
            override fun onResponse(call: Call<ResponseWrapper>, response: Response<ResponseWrapper>) {
                if (response.isSuccessful && response.body() != null) {
                    val responseWrapper = response.body()!!

                    profileImage.setImageResource(R.drawable.avatar_placeholder) // Set default avatar if necessary
                } else {
                    Toast.makeText(requireContext(), "Failed to fetch profile data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseWrapper>, t: Throwable) {
                Toast.makeText(requireContext(), "Network Error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
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
