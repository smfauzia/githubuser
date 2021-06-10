package com.latihan.githubuser.alarmreminder

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.latihan.githubuser.R

class MyPreferenceFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var ALARM: String

    private lateinit var isSwicthMuPreference: SwitchPreference

    companion object {
        private const val DEFAULT_VALUE = "Tidak Ada"
    }

    override fun onCreatePreferences(bundle: Bundle?, s: String?) {
        addPreferencesFromResource(R.xml.preference)
        init()
        setSummaries()
    }

    private fun init() {
        ALARM = resources.getString(R.string.alarm)
        isSwicthMuPreference = findPreference<SwitchPreference>(ALARM) as SwitchPreference
    }

    private fun setSummaries() {
        val sh = preferenceManager.sharedPreferences
        isSwicthMuPreference.isChecked = sh.getBoolean(ALARM, false)
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == ALARM) {
            if (sharedPreferences != null) {
                isSwicthMuPreference.isChecked = sharedPreferences.getBoolean(ALARM, false)
            }
        }
    }
}