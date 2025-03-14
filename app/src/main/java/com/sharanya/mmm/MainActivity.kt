package com.sharanya.mmm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navbar)
        if (savedInstanceState == null) {
            loadFragment(HomeFragment()) // Ensures Home is loaded only once
        }

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> loadFragment(HomeFragment())
                R.id.prev_trips -> loadFragment(Prev_tripFragment())
                R.id.create -> loadFragment(CreateFragment())
                R.id.profile -> loadFragment(ProfileFragment())
                R.id.setting -> loadFragment(SettingsFragment())
            }
            true
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentloader, fragment)
            .commit()
    }
}
