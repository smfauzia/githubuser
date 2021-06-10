package com.latihan.githubuser.alarmreminder

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.latihan.githubuser.R
import com.latihan.githubuser.databinding.ActivityReminderBinding

class ReminderActivity : AppCompatActivity() {

    companion object {
        const val PREFERENSES = "SettingPref"
        private const val DAILY = "daily"
    }

    private lateinit var alarmReceiver: AlarmReceiver
    private lateinit var SharedPreferences: SharedPreferences

    private lateinit var binding: ActivityReminderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReminderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Settings"

        actionBar?.title = "Setting"
        actionBar?.setDisplayHomeAsUpEnabled(true)

        alarmReceiver = AlarmReceiver()
        SharedPreferences = getSharedPreferences(PREFERENSES, Context.MODE_PRIVATE)

        binding.DailySetting.isChecked = SharedPreferences.getBoolean(DAILY, false)
        binding.DailySetting.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                alarmReceiver.setRepeatingAlarm(
                    this,
                    AlarmReceiver.TYPE_REPEATING,
                    getString(R.string.notifikasi)
                )
            } else {
                alarmReceiver.cancelAlarm(this)
            }
            saveChange(isChecked)
        }
    }

    private fun saveChange(value: Boolean) {
        val editor = SharedPreferences.edit()
        editor.putBoolean(DAILY, value)
        editor.apply()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}