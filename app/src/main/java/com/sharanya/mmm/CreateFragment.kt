package com.sharanya.mmm

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class CreateFragment : Fragment() {

    private lateinit var startDestination: EditText
    private lateinit var endDestination: EditText
    private lateinit var addDestinationButton: FloatingActionButton
    private lateinit var dateField: EditText
    private lateinit var descriptionField: EditText
    private lateinit var selectImageButton: Button
    private lateinit var imageRecyclerView: RecyclerView
    private lateinit var createPostButton: Button
    private lateinit var imageAdapter: ImageAdapter
    private val imageList = mutableListOf<Uri>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_create, container, false)

        startDestination = view.findViewById(R.id.startDestination)
        endDestination = view.findViewById(R.id.endDestination)
        addDestinationButton = view.findViewById(R.id.addDestinationButton)
        dateField = view.findViewById(R.id.date)
        descriptionField = view.findViewById(R.id.description)
        imageRecyclerView = view.findViewById(R.id.imageRecyclerView)
        selectImageButton = view.findViewById(R.id.selectImages)
        createPostButton = view.findViewById(R.id.create)

        endDestination.visibility = View.GONE

        imageAdapter = ImageAdapter(imageList)
        imageRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        imageRecyclerView.adapter = imageAdapter

        addDestinationButton.setOnClickListener {
            endDestination.visibility = View.VISIBLE
        }

        selectImageButton.setOnClickListener {
            openImagePicker()
        }

        createPostButton.setOnClickListener {
            savePostToDatabase()
        }

        return view
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == IMAGE_PICK_CODE && resultCode == Activity.RESULT_OK) {
            data?.clipData?.let { clipData ->
                for (i in 0 until clipData.itemCount) {
                    val uri = clipData.getItemAt(i).uri
                    val file = copyUriToFile(uri) // Copy Uri content to a real file
                    file?.let { imageList.add(Uri.fromFile(it)) }
                }
            } ?: data?.data?.let { uri ->
                val file = copyUriToFile(uri) // Copy Uri content to a real file
                file?.let { imageList.add(Uri.fromFile(it)) }
            }

            imageAdapter.notifyDataSetChanged()
        }
    }

    private fun copyUriToFile(uri: Uri): File? {
        val inputStream = requireContext().contentResolver.openInputStream(uri) ?: return null
        val file = File(requireContext().cacheDir, "image_${System.currentTimeMillis()}.jpg")

        file.outputStream().use { outputStream ->
            inputStream.copyTo(outputStream)
        }

        return file
    }


    private fun savePostToDatabase() {
        val startDest = startDestination.text.toString().trim()
        val date = dateField.text.toString().trim()
        val description = descriptionField.text.toString().trim()
        val endDest = if (endDestination.visibility == View.VISIBLE) endDestination.text.toString().trim() else ""

        if (startDest.isEmpty() || date.isEmpty() || description.isEmpty()) {
            Toast.makeText(requireContext(), "Start destination, date, and description are required!", Toast.LENGTH_SHORT).show()
            return
        }

        val startDestBody = startDest.toRequestBody("text/plain".toMediaTypeOrNull())
        val dateBody = date.toRequestBody("text/plain".toMediaTypeOrNull())
        val descriptionBody = description.toRequestBody("text/plain".toMediaTypeOrNull())
        val endDestBody = endDest.toRequestBody("text/plain".toMediaTypeOrNull())

        val imageParts = imageList.mapIndexedNotNull { index, uri ->
            val file = File(uri.path!!)  // Get File from Uri
            val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("images", file.name, requestBody)

        }

        RetrofitClient.instance.savePost(startDestBody, dateBody, descriptionBody, imageParts, endDestBody)
            .enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful) {
                        Toast.makeText(requireContext(), "Post Created Successfully!", Toast.LENGTH_SHORT).show()
                    } else {
                        val errorBody = response.errorBody()?.string()
                        Toast.makeText(requireContext(), "Failed: ${response.code()} - $errorBody", Toast.LENGTH_LONG).show()
                    }
                }


                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e("Upload", "Failed: ${t.message}")
                    Toast.makeText(requireContext(), "Network Error: ${t.message}", Toast.LENGTH_LONG).show()
                }
            })
    }

    private fun getFilePathFromUri(uri: Uri): String? {
        val cursor = requireContext().contentResolver.query(uri, arrayOf(MediaStore.Images.Media.DATA), null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                return it.getString(it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
            }
        }
        return null
    }

    companion object {
        private const val IMAGE_PICK_CODE = 1001
    }
}
