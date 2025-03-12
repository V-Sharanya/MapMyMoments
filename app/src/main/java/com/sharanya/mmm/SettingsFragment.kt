package com.sharanya.mmm

import android.graphics.Color
import android.os.Bundle
import android.view.View // ✅ Import this to fix the error
import androidx.preference.CheckBoxPreference
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listView.setPadding(0, 60, 0, 0) // Adds 32dp top padding
        listView.clipToPadding = false
    // Ensures padding is visible
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.fragment_settings, rootKey)
        loadSetting()

        val chkNightInstant = findPreference<CheckBoxPreference>("NIGHT")
        chkNightInstant?.setOnPreferenceChangeListener { _, newValue ->
            val isNight = newValue as Boolean
            updateBackgroundColor(isNight)
            true
        }

        val listPreference = findPreference<ListPreference>("ORIENTATION")
        listPreference?.setOnPreferenceChangeListener { preference, newValue ->
            val orientation = newValue as String
            updateOrientation(orientation)
            (preference as ListPreference).summary =
                preference.entries[preference.findIndexOfValue(orientation)]
            true
        }
    }

    private fun loadSetting() {
        val sp = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val chkNight = sp.getBoolean("NIGHT", false)
        updateBackgroundColor(chkNight)

        val orientation = sp.getString("ORIENTATION", "false") ?: "false"
        updateOrientation(orientation)
    }

    private fun updateBackgroundColor(isNight: Boolean) {
        val recyclerView = listView
        recyclerView?.setBackgroundColor(
            if (isNight) Color.parseColor("#222222") else Color.parseColor("#ffffff")
        )
    }

    private fun updateOrientation(orientation: String) {
        activity?.requestedOrientation = when (orientation) {
            "1" -> android.content.pm.ActivityInfo.SCREEN_ORIENTATION_BEHIND
            "2" -> android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            "3" -> android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            else -> android.content.pm.ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
    }

    override fun onResume() {
        super.onResume()
        loadSetting()
    }
}
