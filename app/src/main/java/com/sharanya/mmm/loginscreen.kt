package com.sharanya.mmm

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.Button
import android.widget.ImageButton

class loginscreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_loginscreen)
        val backButton = findViewById<ImageButton>(R.id.backtologinbtn)
        backButton.setOnClickListener {
            val intent = Intent(this, login::class.java)
            startActivity(intent)
        }
        val loginButton = findViewById<Button>(R.id.btnlogin)
        loginButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }


}
//
//package com.sharanya.mmm
//
//import android.content.Intent
//import android.os.Bundle
//import android.util.Log
//import android.widget.Button
//import android.widget.ImageButton
//import android.widget.Toast
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.appcompat.app.AppCompatActivity
//import com.google.android.gms.auth.api.signin.GoogleSignIn
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount
//import com.google.android.gms.auth.api.signin.GoogleSignInClient
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.GoogleAuthProvider
//
//class loginscreen : AppCompatActivity() {
//    private lateinit var googleSignInClient: GoogleSignInClient
//    private lateinit var firebaseAuth: FirebaseAuth
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_loginscreen)
//
//        // Initialize Firebase Auth
//        firebaseAuth = FirebaseAuth.getInstance()
//
//        // Configure Google Sign-In
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.default_web_client_id)) // Use Firebase Console ID
//            .requestEmail()
//            .build()
//
//        googleSignInClient = GoogleSignIn.getClient(this, gso)
//
//        val backButton = findViewById<ImageButton>(R.id.backtologinbtn)
//        backButton.setOnClickListener {
//            startActivity(Intent(this, loginscreen::class.java))
//        }
//
//        val loginButton = findViewById<Button>(R.id.btnlogin)
//        loginButton.setOnClickListener {
//            startActivity(Intent(this, MainActivity::class.java))
//        }
//
//        val googleSignInButton = findViewById<Button>(R.id.btn_google_signin) // Add this button in XML
//        googleSignInButton.setOnClickListener {
//            signInWithGoogle()
//        }
//    }
//
//    private val googleSignInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
//        try {
//            val account = task.getResult(Exception::class.java)
//            firebaseAuthWithGoogle(account)
//        } catch (e: Exception) {
//            Log.e("GoogleSignIn", "Google sign-in failed", e)
//            Toast.makeText(this, "Google sign-in failed", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    private fun signInWithGoogle() {
//        val signInIntent = googleSignInClient.signInIntent
//        googleSignInLauncher.launch(signInIntent)
//    }
//
//    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
//        val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
//        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                Toast.makeText(this, "Google Sign-In Successful!", Toast.LENGTH_SHORT).show()
//                startActivity(Intent(this, MainActivity::class.java))
//                finish()
//            } else {
//                Toast.makeText(this, "Authentication Failed!", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//}
