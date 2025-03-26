package com.sharanya.mmm.model

data class LoginResponse(
    val success: Boolean,  // Indicates if login was successful
    val message: String,    // Message from the server
    val token: String? = null  // Optional token if using authentication
)
