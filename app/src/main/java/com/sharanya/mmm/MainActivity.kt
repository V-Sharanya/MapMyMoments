package com.sharanya.mmm


import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val bottomnav = findViewById<BottomNavigationView>(R.id.bottom_navbar)
        loadFragment(HomeFragment())
        bottomnav.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.home -> loadFragment(HomeFragment())
                R.id.prev_trips -> loadFragment(Prev_tripFragment())
                R.id.create -> loadFragment(CreateFragment())
                R.id.profile -> loadFragment(ProfileFragment())
                R.id.setting -> loadFragment(SettingsFragment())
            }
            true
        }
    }
    private fun loadFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentloader,fragment)
            .commit()

    }
}
