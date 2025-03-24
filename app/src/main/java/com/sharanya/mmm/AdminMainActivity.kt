package com.sharanya.mmm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class AdminMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_main)

        val bottomNavigationViewAdmin = findViewById<BottomNavigationView>(R.id.bottomNavigationViewAdmin)

        // Load the default fragment (Dashboard) when activity starts
        if (savedInstanceState == null) {
            replaceFragment(AdminHomeFragment())
        }

        bottomNavigationViewAdmin.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_dashboard -> replaceFragment(AdminHomeFragment())
                R.id.nav_users -> replaceFragment(AdminUsersFragment())
                R.id.nav_settings -> replaceFragment(SettingsFragment())
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.adminfragmentloader, fragment)
            .commit()
    }
}
