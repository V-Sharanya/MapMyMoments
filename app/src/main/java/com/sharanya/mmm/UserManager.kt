package com.sharanya.mmm

import android.content.Context
import android.content.SharedPreferences

class UserManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("UserSession", Context.MODE_PRIVATE)

    fun saveUser(user: users) {
        val editor = sharedPreferences.edit()
        editor.putInt("USER_ID", user.id)  // Save user ID
        editor.putString("USERNAME", user.name)  // Save user name
        editor.putString("EMAIL", user.email)  // Save user email
        editor.putString("PASSWORD", user.password)  // Save password if needed
        editor.apply()
    }

    fun getUser(): users? {
        val name = sharedPreferences.getString("USERNAME", null)
        val email = sharedPreferences.getString("EMAIL", null)
        val password = sharedPreferences.getString("PASSWORD", null)

        return if (name != null && email != null && password != null) {
            users(name, email, password)  // Use your existing constructor
        } else {
            null
        }
    }

    fun clearUser() {
        sharedPreferences.edit().clear().apply()
    }
}
