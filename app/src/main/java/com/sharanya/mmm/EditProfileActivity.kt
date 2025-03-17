import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.sharanya.mmm.R

class EditProfileActivity : AppCompatActivity() {

    private lateinit var profileImage: ImageView
    private lateinit var editName: EditText
    private lateinit var editBio: EditText
    private lateinit var btnSave: Button

    // Registering Activity Result API
    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            profileImage.setImageURI(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        profileImage = findViewById(R.id.img_profile)
        editName = findViewById(R.id.edit_name)
        editBio = findViewById(R.id.edit_bio)
        btnSave = findViewById(R.id.btn_save)

        // Click on Profile Image to Change it
        profileImage.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        // Save Changes
        btnSave.setOnClickListener {
            finish()
        }
    }
}
