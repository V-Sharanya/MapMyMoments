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
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {

    private lateinit var profileImage: ImageView
    private lateinit var cameraIcon: ImageView
    private lateinit var editProfileButton: Button
    private lateinit var setAvatar: TextView
    private lateinit var tvname:TextView
    private lateinit var tvemail:TextView
    private lateinit var changePasswordButton:Button

    private lateinit var tvage:TextView
    private lateinit var tvgender:TextView
    private lateinit var tvbio:TextView

    private var profileAvatar:Int=-1




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

        profileImage = view.findViewById(R.id.profileImage)
        cameraIcon = view.findViewById(R.id.cameraIcon)
        setAvatar = view.findViewById(R.id.setAvatar)
        editProfileButton = view.findViewById(R.id.editProfileButton)

        tvage=view.findViewById(R.id.userAge)
        tvgender=view.findViewById(R.id.userGender)
        tvbio=view.findViewById(R.id.userBio)

        changePasswordButton = view.findViewById(R.id.changePasswordButton)

        changePasswordButton.setOnClickListener {
            val updatepasswordview=LayoutInflater.from(requireContext()).inflate(R.layout.changepasswordfield,null)
            AlertDialog.Builder(requireContext())
                .setView(updatepasswordview)
                .setPositiveButton("Update"){_,_->
                    val newpassword=updatepasswordview.findViewById<TextView>(R.id.newPass).text.toString()
                    val confirmnewpassword=updatepasswordview.findViewById<TextView>(R.id.confirmNewPass).text.toString()
                    if(newpassword!=confirmnewpassword){
                        Toast.makeText(requireContext(),"Passwords do not match",Toast.LENGTH_SHORT).show()
                    }
                    else{
                        updatePassword(userid,newpassword)
                    }
                }
                .setNegativeButton("Cancel"){_,_->}
                .create()
                .show()
        }


        // Set Click Listener
        editProfileButton.setOnClickListener {
            val intent = Intent(requireContext(), EditProfileDetails::class.java)
            intent.putExtra("profileAvatar", profileAvatar)
            startActivity(intent)

            val userId = getUserId()
            fetchProfileDetails(userId)
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

        val userId = getUserId()
        fetchProfileDetails(userId)

        return view
    }

    private fun getUserId():Int{
        val sharedPreferences = requireContext().getSharedPreferences("UserPrefs", AppCompatActivity.MODE_PRIVATE)
        val userId = sharedPreferences.getString("USER_ID", null)
        return userId?.toIntOrNull() ?: -1

    }

    private fun updatePassword(id:Int,password:String){
        val details= mapOf(
            "password" to password,
            "role" to "",
            "email" to "",
            "name" to ""
        )
        val apiservice=RetrofitClient.instance
        val call=apiservice.updateUser(id,details)
        call.enqueue(object : Callback<Unit?> {
            override fun onResponse(call: Call<Unit?>, response: Response<Unit?>) {
                if(response.isSuccessful){
                    Toast.makeText(requireContext(),"password updated successfully",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Unit?>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
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
            profileAvatar=avatarList[position]
            updateavatar(getUserId(),profileAvatar)
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun updateavatar(id:Int,avatar:Int){
        val details= mapOf(
            "bio" to "",
            "age" to "",
            "gender" to "",
            "profileimage" to avatar.toString()
        )
        val apiservice=RetrofitClient.instance
        val call=apiservice.updateprofile(id,details)
        call.enqueue(object : Callback<Unit?> {
            override fun onResponse(call: Call<Unit?>, response: Response<Unit?>) {
                if(response.isSuccessful){
                    Toast.makeText(requireContext(),"avatar updated successfully",Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Unit?>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun fetchProfileDetails(userId: Int) {
        val apiService = RetrofitClient.instance
        val call = apiService.getProfile(userId)
        call.enqueue(object : Callback<responseUser> {
            override fun onResponse(call: Call<responseUser>, response: Response<responseUser>) {
                if (response.isSuccessful && response.body() != null) {
                    val profile = response.body()
                    tvbio.text = profile?.bio ?: "No bio available"
                    tvage.text = profile?.age?.toString() ?: "N/A"
                    tvgender.text = profile?.gender ?: "N/A"
                    if(profile?.profileImage!=null){
                        profileImage.setImageResource(profile.profileImage.toInt())
                    }
                } else {
                    Toast.makeText(requireContext(), "Failed to fetch profile", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<responseUser>, t: Throwable) {
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

}
