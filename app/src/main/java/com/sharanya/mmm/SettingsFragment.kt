package com.sharanya.mmm

import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.preference.*

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listView.setPadding(0, 60, 0, 0) // Adds padding for better UI
        listView.clipToPadding = false
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.fragment_settings, rootKey)
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())

        // 🌙 Night Mode Toggle
        val nightModePref = findPreference<CheckBoxPreference>("NIGHT")
        nightModePref?.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_dark_mode)
        nightModePref?.setOnPreferenceChangeListener { _, newValue ->
            val isNightMode = newValue as Boolean
            AppCompatDelegate.setDefaultNightMode(
                if (isNightMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            )
            requireActivity().recreate() // Fixes redirection issue
            true
        }

        // 🔔 Notifications Toggle
        val notificationsPref = findPreference<CheckBoxPreference>("NOTIFICATIONS")
        notificationsPref?.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_notifications)
        notificationsPref?.setOnPreferenceChangeListener { _, newValue ->
            Toast.makeText(
                requireContext(),
                if (newValue as Boolean) "Notifications Enabled" else "Notifications Disabled",
                Toast.LENGTH_SHORT
            ).show()
            true
        }

        // 🌍 Language Selection
        val languagePref = findPreference<ListPreference>("LANGUAGE")
        languagePref?.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_language)
        languagePref?.setOnPreferenceChangeListener { preference, newValue ->
            val languageIndex = (preference as ListPreference).findIndexOfValue(newValue as String)
            val selectedLanguage = preference.entries[languageIndex] // Corrected toast message
            preference.summary = selectedLanguage
            Toast.makeText(requireContext(), "Language set to $selectedLanguage", Toast.LENGTH_SHORT).show()
            true
        }

        // 🔄 Orientation Selection (Fixed Restart Issue)
        val orientationPref = findPreference<ListPreference>("ORIENTATION")
        orientationPref?.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_orientation)
        orientationPref?.setOnPreferenceChangeListener { _, newValue ->
            val orientation = newValue as String
            requireActivity().requestedOrientation = when (orientation) {
                "portrait" -> ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                "landscape" -> ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                else -> ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            }
            requireActivity().recreate() // Fixes redirection issue
            true
        }
        //Language and font style
        val fontPref = findPreference<ListPreference>("FONT_STYLE")
        fontPref?.setOnPreferenceChangeListener { _, newValue ->
            val fontPath = newValue as String
            PreferenceManager.getDefaultSharedPreferences(requireContext())
                .edit().putString("FONT_STYLE", fontPath).apply()

            Toast.makeText(requireContext(), "Font changed! Restarting app...", Toast.LENGTH_SHORT).show()

            val intent = requireActivity().packageManager
                .getLaunchIntentForPackage(requireActivity().packageName)
            intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent!!)
            requireActivity().finishAffinity()

            true
        }



        // 🔑 Logout
        val logoutPref = findPreference<Preference>("LOGOUT")
        logoutPref?.setOnPreferenceClickListener {
            Toast.makeText(requireContext(), "Logged Out", Toast.LENGTH_SHORT).show()

            // Start LoginActivity
            val intent = Intent(requireContext(), login::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

            true
        }


        // ℹ️ About App Section
        val aboutPref = findPreference<Preference>("ABOUT_APP")
        aboutPref?.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_info)
        aboutPref?.setOnPreferenceClickListener {
            val versionName = requireContext().packageManager.getPackageInfo(requireContext().packageName, 0).versionName
            val aboutDetails = """
                App Name: Map My Moments
                Version: $versionName
                Developed by: Sharanya & Team
            """.trimIndent()
            aboutPref.summary = aboutDetails
            true
        }
    }

    override fun onResume() {
        super.onResume()
        applySettings()
    }

    private fun applySettings() {
        val sp = PreferenceManager.getDefaultSharedPreferences(requireContext())

        // Night Mode
        val isNightMode = sp.getBoolean("NIGHT", false)
        AppCompatDelegate.setDefaultNightMode(
            if (isNightMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )

        // Orientation
        val orientation = sp.getString("ORIENTATION", "portrait") ?: "portrait"
        requireActivity().requestedOrientation = when (orientation) {
            "portrait" -> ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            "landscape" -> ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            else -> ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
    }
}
