package com.lortnoc.foodcove

import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceFragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class PreferenceActivity : AppCompatActivity(),
    PreferenceFragment.OnPreferenceStartFragmentCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (fragmentManager.findFragmentById(android.R.id.content) == null) {
            fragmentManager.beginTransaction()
                .add(android.R.id.content, SettingsFragment()).commit()
        }
    }

    class SettingsFragment : PreferenceFragment() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.settings)

            val preference = findPreference("DARK")
            val darkModePreference: android.preference.CheckBoxPreference =
                findPreference("DARK") as android.preference.CheckBoxPreference

            preference.onPreferenceClickListener = Preference.OnPreferenceClickListener {
                if (darkModePreference.isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else if (!darkModePreference.isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
                true
            }
        }
    }

    override fun onPreferenceStartFragment(
        caller: PreferenceFragment?,
        pref: Preference?
    ): Boolean {
        TODO("Not yet implemented")
    }
}
